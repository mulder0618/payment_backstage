package com.zhx.backstage.merchant.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhx.backstage.alipay.core.AlipayQuery;
import com.zhx.backstage.merchant.service.MerchantService;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mulder on 2015/6/16.
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController {


    @Autowired
    MerchantService merchantService;


    /**
     * 显示订单列表页
     * @param modelAndView
     * @return
     */
    @RequestMapping("/showmerchantlist")
    public ModelAndView showorderlist(
            ModelAndView modelAndView
    ){
        modelAndView.setViewName("merchant/merchantlist");
        return modelAndView;
    }


    /**
     * 查询订单列表
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping("/listmerchant")
    @ResponseBody
    public Map loadorderlist(
            @RequestParam(value = "MerchantName", required = false) String MerchantName,
            @RequestParam(value = "CreateDate", required = false) String CreateDate,
            @RequestParam(value = "Status", required = false) String Status,
            Page page
    ) throws Exception {
        Map param =new HashMap();
        if(MerchantName!=null&&MerchantName.trim().length()>0) {
            param.put("MerchantName", MerchantName);
        }
        if(CreateDate!=null&&CreateDate.trim().length()>0) {
            param.put("CreateDate", CreateDate);
        }
        if(Status!=null&&Status.trim().length()>0&&!"2".equals(Status)) {
            param.put("Status", Status);
        }
        return merchantService.getMerchantList(param,page.getPage(), page.getRows());
    }
    /**
     * 显示添加用户页面
     * @param modelAndView
     * @return
     */
    @RequestMapping("/showmeradd")
    public ModelAndView showuseradd(
            ModelAndView modelAndView
    ){
        modelAndView.setViewName("merchant/meradd");
        return modelAndView;
    }
    /**
     * 显示添加用户页面
     * @param modelAndView
     * @return
     */
    @RequestMapping("/showmerupd")
    public ModelAndView showmerupd(
            ModelAndView modelAndView
    ){
        modelAndView.setViewName("merchant/merupdate");
        return modelAndView;
    }

    @RequestMapping("toadd")
    @ResponseBody
    public int toadd(
            @RequestParam(value = "merId", required = true) String merId,
            @RequestParam(value = "merName", required = true) String merName,
            @RequestParam(value = "status", required = true) String status,
            @RequestParam(value = "Authcode", required = true) String Authcode,
            @RequestParam(value = "SupportPaymentWay", required = true) String SupportPaymentWay
    ) throws Exception {
        Map param=new HashMap();
        param.put("MerchantID",merId);
        param.put("MerchantName",merName);
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        param.put("CreateDate",sdf.format(new Date()));
        param.put("status",status);
        param.put("Authcode",Authcode);
        param.put("SupportPaymentWay",SupportPaymentWay);


        return merchantService.insertMerchant(param);

    }

    @RequestMapping("updatestat")
    @ResponseBody
    public String updatestat( @RequestParam(value = "array", required = true) String array){
            JSONObject s= JSON.parseObject(array);
            List<Map> l=(List)s.get("array");
            String res="1";
            for(Map map:l){
                Map m=new HashMap();
                String id=map.get("id").toString();
                m.put("ID",id);
                String sta=map.get("status").toString();
                if("1".equals(sta)){
                    m.put("status",0);
                }else if("0".equals(sta)){
                    m.put("status",1);
                }
                int i= merchantService.updateMerchant(m);
                String ids="";
                if(i!=1){
                    ids+=id+",";

                    res=ids;
                }
            }

            return res;
        }
    @RequestMapping("updInfo")
    @ResponseBody
    public int addInfo(
            @RequestParam(value = "ID", required = true) String id,
            @RequestParam(value = "merId", required = true) String merId,
            @RequestParam(value = "merName", required = true) String merName,
            @RequestParam(value = "status", required = true) String status,
            @RequestParam(value = "Authcode", required = true) String Authcode,
            @RequestParam(value = "SupportPaymentWay", required = true) String SupportPaymentWay
    ) throws Exception {
        Map param=new HashMap();
        param.put("MerchantID",merId);
        param.put("MerchantName",merName);
        param.put("status",status);
        param.put("Authcode",Authcode);
        //param.put("SupportPaymentWay",SupportPaymentWay);
        param.put("ID",id);


        return merchantService.updateMerchant(param);

    }

}
