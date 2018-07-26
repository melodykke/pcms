package com.gzzhsl.pcms.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Slf4j
public class ShortNetAddressUtil {
    public static int TIMEOUT = 10 * 1000;
    public static String ENCODING = "UTF-8";

    /**
     * 根据传入的URL 通过访问百度短链接的接口，将其转换为短链接
     */
    public static String getShortURL(String originURL) {
        String tinyUrl = null;
        try {
            // 指定百度短链接的接口
            URL url = new URL("http://dwz.cn/create.php");
            // 建立连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置连接参数 使用连接进行输出
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 不使用缓存
            connection.setUseCaches(false);
            // 设置连接超时时间为30秒
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            // 设置请求模式为POST
            connection.setRequestMethod("POST");
            // 设置POST信息，这里为传入的原始URL
            String postData = URLEncoder.encode(originURL.toString(), "utf-8");
            // 输出原始的url
            connection.getOutputStream().write(("url=" + postData).getBytes());
            // 连接百度短链接接口
            connection.connect();
            // 获取返回的字符串
            String responseStr = getResponseStr(connection);
            log.info("response string:" + responseStr);
            // 在字符串里获取tinyurl，即短链接
            tinyUrl = getValueByKey(responseStr, "tinyurl");
            log.info("tinyurl:" + tinyUrl);
            // 关闭连接
            connection.disconnect();
        } catch (IOException e) {
            log.error("getshortURL error:"+e.toString());
        }
        return tinyUrl;
    }

    /**
     * 通过HttpConnection 获取返回的字符串
     */
    private static String getResponseStr(HttpURLConnection connection) throws IOException {
        StringBuffer result = new StringBuffer();
        // 从连接中获取http状态码
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 如果返回的状态码OK时，那么去除连接的输出流
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, ENCODING));
            String inputLine = "";
            while((inputLine = reader.readLine()) != null) {
                // 将消息逐行读入到结果中
                result.append(inputLine);
            }
        }
        // 将结果转换成String 并返回
        return String.valueOf(result);
    }

    /**
     * JSON 依据传入的KEY 获取value
     */
    private static String getValueByKey(String replyText, String key) {
        ObjectMapper mapper = new ObjectMapper();
        // 定义json节点
        JsonNode node;
        String targetValue = null;
        try {
            // 把调用返回的消息串转换成json对象
            node = mapper.readTree(replyText);
            // 依据key 从 json对象里获取对应的值
            targetValue = node.get(key).asText();
        } catch (JsonProcessingException e) {
            log.error("getValueByKey error:" + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("getValueByKey error:" + e.toString());
        }
        return targetValue;
    }

    /**
     * main 测试
     */
    public static void main(String[] args) {
        getShortURL("https://blog.csdn.net/lyflyyvip/article/details/78297051");
    }
}
