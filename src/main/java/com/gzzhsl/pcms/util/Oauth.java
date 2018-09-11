package com.gzzhsl.pcms.util;

/**
 * 认证接口示例
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

public class Oauth {

    private static final Log log = LogFactory.getLog(Oauth.class);
    private static String uuid = "561c2165-0c8e-4243-b47c-c75de88fcd11";  //应用注册ID   权限系统提供
    private static String sign = "3d161ca4-52b0-43d3-99b0-8b217011587f";  //应用key  权限系统提供
    private static String version = "1.0"; //版本  权限系统提供
    private static String clientid = "16"; //应用ID   权限系统提供

    /**
     * token验证接口
     * @param url  认证接口
     * @param token  token
     * @return 结果json数据字符
     */
    public static String sendGet(String url,String token) {
        log.info("url:"+url);
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpClient = HttpClients.createDefault();
            int CONNECTION_TIMEOUT = 10 * 1000;
            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECTION_TIMEOUT).setConnectTimeout(CONNECTION_TIMEOUT).setSocketTimeout(CONNECTION_TIMEOUT).build();
            httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            httpGet.addHeader("Content-Type", "application/json");
            httpGet.addHeader("access_token", token);
            httpGet.addHeader("uuid", uuid);
            httpGet.addHeader("sign", sign);
            httpGet.addHeader("version", version);
            httpGet.addHeader("clientid", clientid);
            httpResponse = httpClient.execute(httpGet, new BasicHttpContext());
            HttpEntity entity = httpResponse.getEntity();
            if(entity != null) {
                String resultStr = EntityUtils.toString(entity, "UTF-8");
                log.info("resultStr:"+resultStr);
                return resultStr;
            }
        } catch(Exception e) {
            log.error("", e);
        } finally {
            if(httpResponse != null) {
                try {
                    httpResponse.close();
                } catch(Exception e) {
                }
            }
            if(httpGet != null) {
                try {
                    httpGet.releaseConnection();
                } catch(Exception e) {
                }
            }
            if(httpClient != null) {
                try {
                    httpClient.close();
                } catch(Exception e) {
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        //TOken认证接口    成功失败数据格式请参看文档
        String st = sendGet("http://114.215.134.193:20350/me","d30e0b80-e64d-4bea-ad32-9dbf0bb2f2fb");
        System.out.println(st);
    }
}

