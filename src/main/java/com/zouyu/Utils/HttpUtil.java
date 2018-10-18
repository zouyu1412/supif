package com.zouyu.Utils;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpUtil {

    Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public String doGetWithUrl(String url) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        //创建httpClient对象
        CloseableHttpResponse response = null;

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        try {
            //执行请求
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            //判断返回状态码是否为200
            if (statusCode == 200 || statusCode == 500) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                return content;
            } else {
                logger.error("获取cloudt组织信息失败，请求url:[{}]", url);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Test
    public void test(){
        String url = "http://www.baidu.com";
        String content = new HttpUtil().doGetWithUrl(url);
        System.out.println(content);
    }
}
