package com.zhx.backstage.alipay.core;

import com.zhx.backstage.alipay.config.AlipayConfig;
import com.zhx.base.util.XmlUtils;
import org.dom4j.Document;
import org.dom4j.Node;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mulder on 15/12/11.
 */
public class AlipayQuery {

    public Map queryOrderStatus(HttpServletRequest request) throws UnsupportedEncodingException {
        //支付宝交易号
        //String trade_no = "2015120121001004030212849225";
        String trade_no = new String(request.getAttribute("WIDtrade_no").toString().getBytes("ISO-8859-1"), "UTF-8");
        //支付宝交易号与商户网站订单号不能同时为空

        //商户订单号
        //String out_trade_no = "zhxgw10000798522";
        String out_trade_no = new String(request.getAttribute("WIDout_trade_no").toString().getBytes("ISO-8859-1"), "UTF-8");

        //////////////////////////////////////////////////////////////////////////////////

        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "single_trade_query");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("trade_no", trade_no);
        sParaTemp.put("out_trade_no", out_trade_no);

        String is_success = null;
        String error = null;
        double alipayAmount = 0;
        try {
            String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
            is_success = XmlUtils.findTagVal("is_success", sHtmlText);
            error = XmlUtils.findTagVal("error", sHtmlText);
            alipayAmount = Double.valueOf(XmlUtils.findTagVal("price", sHtmlText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map result = new HashMap();
        result.put("alipayAmount",alipayAmount);
        result.put("is_success",is_success);
        result.put("error",error);
        return result;
    }
}
