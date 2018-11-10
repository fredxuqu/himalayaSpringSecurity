package com.himalaya.auth.util;

import com.himalaya.auth.constant.SdkConstant;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by xuqu on 2018/9/11.
 */
public class HttpClientUtilTest {
        
	@Test
	public void testGetIndex() {
		// corp getlist
		String url = "http://127.0.0.1:8080/auth";
		String response = HttpClientPostJSON.sendGet(url);
		System.out.println(response);
	}
	
	@Test
	public void testGet() {
		// corp getlist
		String url = "http://127.0.0.1:8080/auth/index?flag=10100101";
		String response = HttpClientPostJSON.sendGet(url);
		System.out.println(response);
	}

	@Test
	public void testPost() {
		// corp getlist
		String url = "http://localhost:8080/auth/user";
		StringBuffer data = new StringBuffer("{");
		data.append("\"flag\":\"101001010100\"");
		data.append("}");
		String response = HttpClientPostJSON.sendPost(url, data.toString());
		System.out.println(response); 
	}

	@Test
	public void testSignPost(){
		
		String method = "POST";
        String appKey = "GUEST";
        String appSecret = "123456";
        String version = "1.0";
        String timestamp = String.valueOf(System.currentTimeMillis());

        Map<String, String> headerParams = new HashMap<String, String>();
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_KEY, appKey);
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_VERSION, version);
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_TIMESTAMP, timestamp);

        String url = "http://127.0.0.1:8080/auth/post";

//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("flag", "10100101");
//        Map<String, String> formParams = null;
        String sign = SignUtil.sign(method, appSecret, headerParams, url);
        
        // prepare http request
        String requestURL = "http://127.0.0.1:8080/auth/post";
		HttpPost httpRequst = new HttpPost(requestURL);
		
		// set data
		StringBuffer data = new StringBuffer("{");
		data.append("\"flag\":\"10100101\"");
		data.append("}");
		StringEntity stringentity = new StringEntity(data.toString(),
                ContentType.create("application/json", "utf-8"));
		httpRequst.setEntity(stringentity);
		
		// set header
		httpRequst.addHeader(SdkConstant.CLOUDAPI_X_CA_KEY, appKey);
		httpRequst.addHeader(SdkConstant.CLOUDAPI_X_CA_VERSION, version);
		httpRequst.addHeader(SdkConstant.CLOUDAPI_X_CA_TIMESTAMP, timestamp);
		httpRequst.addHeader(SdkConstant.CLOUDAPI_X_CA_SIGNATURE, sign);

		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse httpResponse = httpclient.execute(httpRequst);
			System.out.println(httpResponse.getStatusLine().getStatusCode());
			HttpEntity httpEntity = httpResponse.getEntity();
			String result = EntityUtils.toString(httpEntity);// 取出应答字符串
			System.out.println("[Thirdpart CallAPIDemo][result] " + result);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[Thirdpart Order][result] " + e);
		}
	}
	
	@Test
	public void testSignGet(){
		
		String method = "GET";
        String appKey = "GUEST";
        String appSecret = "123456";
        String version = "1.0";
        String timestamp = String.valueOf(System.currentTimeMillis());

        Map<String, String> headerParams = new HashMap<String, String>();
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_KEY, appKey);
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_VERSION, version);
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_TIMESTAMP, timestamp);

        String url = "http://127.0.0.1:8080/auth/get";
        
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("flag", "10100101");
//        Map<String, String> formParams = null;
        String sign = SignUtil.sign(method, appSecret, headerParams, url);
        
        
        // prepare http request
        String requestURL = "http://127.0.0.1:8080/auth/get?flag=10100101";
		HttpGet httpRequst = new HttpGet(requestURL);
		
		httpRequst.addHeader(SdkConstant.CLOUDAPI_X_CA_KEY, appKey);
		httpRequst.addHeader(SdkConstant.CLOUDAPI_X_CA_VERSION, version);
		httpRequst.addHeader(SdkConstant.CLOUDAPI_X_CA_TIMESTAMP, timestamp);
		httpRequst.addHeader(SdkConstant.CLOUDAPI_X_CA_SIGNATURE, sign);

		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse httpResponse = httpclient.execute(httpRequst);
			System.out.println(httpResponse.getStatusLine().getStatusCode());
			HttpEntity httpEntity = httpResponse.getEntity();
			String result = EntityUtils.toString(httpEntity);// 取出应答字符串
			System.out.println("[Thirdpart CallAPIDemo][result] " + result);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[Thirdpart Order][result] " + e);
		} 
	}
}