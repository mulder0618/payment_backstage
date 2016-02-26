package com.zhx.backstage.order.controller;

import com.zhx.backstage.alipay.core.AlipayQuery;
import com.zhx.backstage.order.service.OrderService;
import com.zhx.base.model.Page;
import com.zhx.base.util.HttpUtils;
import com.zhx.base.util.sign.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by mulder on 2015/6/16.
 */
@Controller
@RequestMapping("/order")
public class OrderController {


    @Autowired
    OrderService orderService;


    /**
     * 显示订单列表页
     * @param modelAndView
     * @return
     */
    @RequestMapping("/showorderlist")
    public ModelAndView showorderlist(
            ModelAndView modelAndView
    ){
        modelAndView.setViewName("order/orderlist");
        return modelAndView;
    }


    /**
     * 查询订单列表
     * @param orderStatus
     * @param startTime
     * @param endTime
     * @param orderNo
     * @param sort
     * @param order
     * @param gatewayOrderNo
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping("/loadorderlist")
    @ResponseBody
    public Map loadorderlist(
            @RequestParam(value = "orderStatus", required = false) String orderStatus,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "orderNo", required = false) String orderNo,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "gatewayOrderNo", required = false) String gatewayOrderNo,
            Page page
    ) throws Exception {
        return orderService.getOrderList(page.getPage(), page.getRows(),orderStatus,startTime,endTime,orderNo,gatewayOrderNo,sort,order);
    }


    /**
     * 获取订单状态
     * @return
     * @throws Exception
     */
    @RequestMapping("/loadpayorderstatus")
    @ResponseBody
    public List<Map> loadpayorderstatus(
    ) throws Exception {
        List orderStatusList = new ArrayList();
        Map orderStatus0 = new HashMap();
        orderStatus0.put("id","");
        orderStatus0.put("text","全部");
        orderStatus0.put("selected",true);
        orderStatusList.add(orderStatus0);

        Map orderStatus1 = new HashMap();
        orderStatus1.put("id",0);
        orderStatus1.put("text","未付款");
        orderStatusList.add(orderStatus1);

        Map orderStatus2 = new HashMap();
        orderStatus2.put("id",1);
        orderStatus2.put("text","已付款");
        orderStatusList.add(orderStatus2);
        return orderStatusList;
    }


    /**
     * 手工修改支付状态
     * @param payID
     * @param orderNO
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/modifyorder")
    @ResponseBody
    public Map modifyorder(
            @RequestParam(value = "payID", required = true) String payID,
            @RequestParam(value = "orderNO", required = true) String orderNO,
            @RequestParam(value = "merchantID", required = true) String merchantID,
            @RequestParam(value = "amount", required = true) String amount,
            HttpServletRequest request
    ) throws UnsupportedEncodingException {
        //调用支付宝查询订单状态接口
        AlipayQuery alipayQuery = new AlipayQuery();
        request.setAttribute("WIDtrade_no",orderNO);
        request.setAttribute("WIDout_trade_no",payID);
        Map alipayStatus = alipayQuery.queryOrderStatus(request);
        if("T".equals(alipayStatus.get("is_success"))){       //支付成功
            double alipayAmount = Double.valueOf(alipayStatus.get("alipayAmount").toString());
            if(alipayAmount==Double.valueOf(amount)){
                //推送商户后台
                Map paymentOrder = orderService.getPaymentOrderByPayID(payID,"0");
                String merchantOrderNO = paymentOrder.get("OrderNO").toString();
                String notifyURL = paymentOrder.get("NotifyURL").toString();
                //获取商户密钥
                Map merchant = orderService.getPaymentMerchant(merchantID, "1");
                String authcode = merchant.get("authcode").toString();
                //更新网关订单状态
                orderService.updateOrder(payID,"1");
                Map param = new HashMap();
                param.put("payorder",merchantOrderNO);
                param.put("amount",amount);
                param.put("trade_status","success");
                String sign = SignUtil.bulidSign(param, authcode);
                param.put("sign",sign);
                HttpUtils.doPost(notifyURL, param, null);
            }
            else{
                alipayStatus.put("is_success","F");
                alipayStatus.put("error","平台返回金额不一致");
            }
        }
        return alipayStatus;
    }


    @RequestMapping("/queryorder")
    public ModelAndView queryorder(

            ModelAndView modelAndView
    ){
        modelAndView.setViewName("alipay/alipayapi");
        return modelAndView;
    }



}
