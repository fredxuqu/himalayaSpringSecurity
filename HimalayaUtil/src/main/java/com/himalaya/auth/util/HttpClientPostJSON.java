package com.himalaya.auth.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2017年10月26日 下午7:17:44
* Description
*/
public class HttpClientPostJSON {

	//post请求方法
	public static String sendGet(String url) {
		String response = null;

		try {
			CloseableHttpClient httpclient = null;
			CloseableHttpResponse httpresponse = null;
			try {
				httpclient = HttpClients.createDefault();
				HttpGet httpget = new HttpGet(url);
				httpresponse = httpclient.execute(httpget);
				response = EntityUtils
						.toString(httpresponse.getEntity());
			} finally {
				if (httpclient != null) {
					httpclient.close();
				}
				if (httpresponse != null) {
					httpresponse.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	//post请求方法
    public static String sendPost(String url, String data) {
        String response = null;

        try { 
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;
            try {
                httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(url);
                StringEntity stringentity = new StringEntity(data,
                        ContentType.create("application/json", "utf-8"));
                httppost.setEntity(stringentity);
                httpresponse = httpclient.execute(httppost);
                response = EntityUtils
                       .toString(httpresponse.getEntity());
            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (httpresponse != null) {
                    httpresponse.close();
                }
            }
       } catch (Exception e) {
            e.printStackTrace();
       }
       return response;
    }
}