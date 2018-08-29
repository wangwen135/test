package com.wwh.test.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * <pre>
 * apach 的官方示例
 * </pre>
 *
 * @author wwh
 * @date 2015年9月19日 下午7:06:51
 *
 */
public class ApachExample {

    /*
     * Request.Get("http://targethost/homepage") .execute().returnContent();
     * Request.Post("http://targethost/login")
     * .bodyForm(Form.form().add("username", "vip").add("password",
     * "secret").build()) .execute().returnContent();
     */
    public static void main(String[] args) throws Exception {

        
      //我们可以使用一个Builder来设置UA字段，然后再创建HttpClient对象
        HttpClientBuilder builder = HttpClients.custom();
        //对照UA字串的标准格式理解一下每部分的意思
//        builder.setUserAgent("Mozilla/5.0(Windows;U;Windows NT 5.1;en-US;rv:0.9.4)");       
        builder.setUserAgent("Mozilla/5.0 (Windows NT 6.1; rv:45.0) Gecko/20100101 Firefox/45.0");
        
        
        final CloseableHttpClient httpclient = builder.build();

        //CloseableHttpClient httpclient = HttpClients.createDefault();

        
        try {
//            HttpGet httpGet = new HttpGet("http://zqktlwi4fecvo6ri.onion/");
            HttpGet httpGet = new HttpGet("http://rfyqcextgeq4panu.onion/");
            
            

            
         // 设置一个代理测试一下
            HttpHost proxy = new HttpHost("127.0.0.1", 8118, "http");
            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            
            httpGet.setConfig(config);
            
            //设置http 请求头
//            httpGet.seth
            
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            /*
             * The underlying HTTP connection is still held by the response
             * object to allow the response content to be streamed directly from
             * the network socket. In order to ensure correct deallocation of
             * system resources the user MUST call CloseableHttpResponse#close()
             * from a finally clause. Please note that if response content is
             * not fully consumed the underlying connection cannot be safely
             * re-used and will be shut down and discarded by the connection
             * manager.
             */
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed

                // 读取字符串
                String rpString = EntityUtils.toString(entity1);
                System.out.println(rpString);

                EntityUtils.consume(entity1);
            } finally {
                response1.close();
            }

            if(1==1) {
                return;
            }
            
            
            HttpPost httpPost = new HttpPost("http://httpbin.org/post");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("username", "vip"));
            nvps.add(new BasicNameValuePair("password", "secret"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            try {
                System.out.println(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed

                // 读取字符串
                String rpString = EntityUtils.toString(entity2);
                System.out.println(rpString);

                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
