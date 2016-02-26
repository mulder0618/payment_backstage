package com.zhx.backstage.order.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zhx.backstage.order.mapper.OrderMapper;
import com.zhx.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mulder on 15/6/16.
 */
@Service("orderService")
public class OrderService extends BaseService {

    @Autowired
    OrderMapper orderMapper;


    /**
     * 查询订单列表
     * @param pageIndex  当前页码
     * @param pageSize     分页条数
     * @return
     * @throws Exception
     */
    public Map getOrderList(
            int pageIndex,
            int pageSize,
            String orderStatus,
            String startTime,
            String endTime,
            String orderNo,
            String gatewayOrderNo,
            String sort,
            String order)
            throws Exception
    {
        Map queryOrder = new HashMap();
        if(orderStatus!=null&&!"".equals(orderStatus)){
            queryOrder.put("orderStatus",orderStatus);
        }
        if(startTime!=null&&!"".equals(startTime)){
            queryOrder.put("startTime",startTime);
        }
        if(endTime!=null&&!"".equals(endTime)){
            queryOrder.put("endTime",endTime);
        }
        if(orderNo!=null&&!"".equals(orderNo)){
            queryOrder.put("orderNo",orderNo);
        }
        if(gatewayOrderNo!=null&&!"".equals(gatewayOrderNo)){
            queryOrder.put("gatewayOrderNo",gatewayOrderNo);
        }
        if(sort!=null){
            queryOrder.put("sort",sort);
        }
        if(order!=null){
            queryOrder.put("order",order);
        }
        PageList<Map> orderPagerList = (PageList<Map>) getPageList(OrderMapper.class, "selectOrderList", queryOrder, pageIndex, pageSize);
        return packageEasyuiResultMap(orderPagerList);
    }


    /**
     * 更新订单信息
     * @param payID
     * @param status
     */
    @Transactional
    public void updateOrder(String payID,String status){
        Map paymentOrder = new HashMap();
        paymentOrder.put("payID",payID);
        paymentOrder.put("status",status);     //0未支付1已支付
        paymentOrder.put("payTime",new Date());     //0未支付1已支付
        orderMapper.updatePaymentOrder(paymentOrder);
    }


    /**
     * 通过网关订单号查询商户请求信息
     * @param payID
     * @return
     */
    public Map getPaymentOrderByPayID(String payID,String status){
        Map paymentOrder = new HashMap();
        paymentOrder.put("payID",payID);
        paymentOrder.put("status",status);
        return orderMapper.selectPaymentOrder(paymentOrder);
    }

    /**
     * 查询商户密钥
     * @param merchantID
     * @param status
     * @return
     */
    public Map getPaymentMerchant(String merchantID,String status){
        Map queryMerchant = new HashMap();
        queryMerchant.put("merchantID",merchantID);
        queryMerchant.put("status",status);
        return orderMapper.selectPaymentMerchant(queryMerchant);
    }




}
