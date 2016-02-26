package com.zhx.base.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;


public class HttpUtils {
    private static Logger log = Logger.getLogger(HttpUtils.class.toString());
    public final static int connectTimeout = 20000;
    public CloseableHttpClient getHttpClient() {
        return HttpClients.createDefault();
//        return BaseHttpClient.getHttpclient();
    }


    /**
     * https  post请求
     * @param url
     * @param map
     * @return
     */
    public static String doPost(String url, Map map, Object headp) {
        String result = null;
        CloseableHttpResponse response=null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(connectTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectTimeout).build();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            // 设置Cookie
            if (headp != null) {
                Header[] header = (Header[]) headp;
                for (Header he : header) {
                    if ("Set-Cookie".equals(he.getName())) {
                        httpPost.addHeader(he);
                    }
                }
            }
            //httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
            //设置参数
            StringBuilder sb = new StringBuilder();
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Iterator it = map.keySet().iterator(); it.hasNext(); ) {
                Object key = it.next();
                list.add(new BasicNameValuePair(String.valueOf(key), String.valueOf(map.get(key))));
                sb.append(String.valueOf(key)+"="+String.valueOf(map.get(key)));
                sb.append("*********");
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = null;
                entity = new UrlEncodedFormEntity(list, "UTF-8");
                httpPost.setEntity(entity);
            }
            response =httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(response!=null){
                    response.close();
                }
                if(httpClient!=null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * http  get请求
     *
     * @param url
     * @param headp
     * @return
     */
    public  String doGet(String url, Object headp) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response=null;
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(connectTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectTimeout).build();
        try {
            HttpGet httpget = new HttpGet(url);
            httpget.setConfig(requestConfig);
            // 设置Cookie
            if (headp != null) {
                Header[] header11 = (Header[]) headp;
                for (Header he : header11) {
                    if ("Set-Cookie".equals(he.getName())) {
                        httpget.addHeader(he);
                    }
                }
            }
            log.info("调用外部接口地址+参数："+url);
            response = getHttpClient().execute(httpget);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(response!=null){
                    response.close();
                }
                if(httpClient!=null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
      /*  Map param = new HashMap();
        param.put("out_trade_no","zhxgw2778891");
        param.put("trade_no","112233zfb");
        param.put("trade_status","success");
        HttpUtils.doPost("http://localhost:8080/alipay/notify", param, null);*/
        /*double a = 1;
        double b = 1.00;
        System.out.println(a==b);*/
        Map param = new HashMap();
        param.put("order","123");
        param.put("amount","sdf");
        param.put("trade_status","success");
        param.put("sign","k13231s");
        System.out.println(param.toString());
    }


}
