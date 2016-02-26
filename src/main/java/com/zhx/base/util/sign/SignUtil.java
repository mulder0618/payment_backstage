package com.zhx.base.util.sign;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 供验证APP签名，
 * Created by mulder on 2015/9/24.
 */
public class SignUtil implements Comparable<SignUtil> {

    private static Logger log = Logger.getLogger(String.valueOf(SignUtil.class));

    public String s;

    public SignUtil(String s) {
        this.s = s;
    }


    /**
     * 返回服务端签名
     * @param paramMap
     * @return
     */
    public static String buildServerSign(Map paramMap,String authcode){
        Iterator<Map.Entry<String, String[]>> entries = paramMap.entrySet().iterator();
        String ss[] = new String[paramMap.size()-1];
        int index = 0;
        while (entries.hasNext()) {
            Map.Entry<String, String[]> entry = entries.next();
            String key = entry.getKey();
            if ("sign".equals(key)){
                continue;
            }
            else{
                ss[index] = key;
                index++;
            }
        }
        SignUtil mySs[]=new SignUtil[ss.length];//创建自定义排序的数组
        for (int i = 0; i < ss.length; i++) {
            mySs[i]=new SignUtil(ss[i]);
        }
        StringBuffer paramBuffer = new StringBuffer();
        Arrays.sort(mySs);//排序
        for (int i = 0; i < mySs.length; i++) {
            String key = mySs[i].s;
            paramBuffer.append(key);
            paramBuffer.append("=");
            try{
                String[] val = (String[]) paramMap.get(key);
                paramBuffer.append(val[0]);
            }
            catch (Exception e){
                paramBuffer.append(paramMap.get(key));
            }
            paramBuffer.append("&");
        }
        String salt = authcode;
        String endparamBuffer = paramBuffer.substring(0, paramBuffer.length() - 1);

       /* String encodeparam="";
        try {
            encodeparam   =   URLEncoder.encode(endparamBuffer, "utf-8");
            log.info("URLEncoder.decode   :" + encodeparam);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        String paramSha1 = Sha1Util.encodeSha1(endparamBuffer.toString(),salt );
        log.info("salt: " + salt);
        log.info("接收到的参数: " + endparamBuffer);
        log.info("sha1结果: "+paramSha1);
        return paramSha1;
    }

    /**
     * 按ascii码排序签名字段
     * @param paramMap
     */
    public static String bulidSign(Map paramMap , String authcode){
        Iterator<Map.Entry<String, String[]>> entries = paramMap.entrySet().iterator();
        String ss[] = new String[paramMap.size()];
        int index = 0;
        while (entries.hasNext()) {
            Map.Entry<String, String[]> entry = entries.next();
            String key = entry.getKey();
            ss[index] = key;
            index++;
        }
        SignUtil mySs[]=new SignUtil[ss.length];//创建自定义排序的数组
        for (int i = 0; i < ss.length; i++) {
            mySs[i]=new SignUtil(ss[i]);
        }
        StringBuffer paramBuffer = new StringBuffer();
        Arrays.sort(mySs);//排序
        for (int i = 0; i < mySs.length; i++) {
            String key = mySs[i].s;
            paramBuffer.append(key);
            paramBuffer.append("=");
            try{
                String[] val = (String[]) paramMap.get(key);
                paramBuffer.append(val[0]);
            }
            catch (Exception e){;
                paramBuffer.append(paramMap.get(key));
            }
            paramBuffer.append("&");
        }
        String salt = authcode;
        String endparamBuffer = paramBuffer.substring(0, paramBuffer.length() - 1);
       /* String encodeparam="";
        try {
            encodeparam =   URLEncoder.encode(endparamBuffer, "utf-8");
            log.info("URLDecoder.decode   :" + encodeparam);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        String paramSha1 = Sha1Util.encodeSha1(endparamBuffer.toString(),salt );
        log.info("salt: " + salt);
        log.info("接收到的参数: " + endparamBuffer);
        log.info("sha1结果: "+paramSha1);
        return paramSha1;
    }


    @Override
    public int compareTo(SignUtil o) {
        if (o == null || o.s == null) return 1;
        if (s.length() > o.s.length()) return 1;
        else if (s.length() < o.s.length()) return -1;
        return s.compareTo(o.s);
    }



    public  static  void main(String[] args){
        //用于生成客户端生成签名
        Map map = new HashMap();
        map.put("currencyCode", "1");
        map.put("orderNO", "201504411680310026");
        map.put("merchantID", "100001");
        map.put("amount","0.01");
        map.put("paymentWay","101");
        map.put("goodsurl","");
        map.put("description","http://139.217.29.159:8080/recharge/showGoodsInfoPage?goodsid=100002");
        map.put("returnURL","http://139.217.29.159:8080/recharge/payresult");
        map.put("notifyURL","http://139.217.29.159:8080/recharge/payBackPush");
        map.put("version","1");
        System.out.println(SignUtil.bulidSign(map, "123456"));

        //map.put("sign", "3214123125465645654645654");

    }
}