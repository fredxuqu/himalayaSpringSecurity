package com.himalaya.auth.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年9月20日 上午10:29:17
* Description
*/
public class BodyHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private final byte[] requestBody;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(BodyHttpServletRequestWrapper.class);
	
	public BodyHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		String requestStream = getRequestBody(request);
		requestBody = requestStream.getBytes(Charset.forName("UTF-8"));
	}
	
	public String getRequestBody(){
		if(requestBody!=null && requestBody.length>0){
			return new String(requestBody, Charset.forName("UTF-8"));
		} else {
			return null;
		}
	}
	
	/**
	 * get request stream
	 * @param request
	 * @return
	 */
	private String getRequestBody(final HttpServletRequest request){
		StringBuilder stringBuilder = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		try {
			inputStream = cloneInputStream(request.getInputStream());
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String line = "";
			while((line = bufferedReader.readLine()) != null){
				stringBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bufferedReader!=null){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if(LOGGER.isDebugEnabled()){
			LOGGER.info(stringBuilder.toString());
		}
		return stringBuilder.toString();
	}
	
	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody);
		
		return new ServletInputStream() {
			
			@Override
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}
			
			@Override
			public void setReadListener(ReadListener listener) {
			}
			
			@Override
			public boolean isReady() {
				return false;
			}
			
			@Override
			public boolean isFinished() {
				return false;
			}
		};
	}
	
	/**
	 * Clone ServletInputStream
	 * @param inputStream
	 * @return
	 */
	private InputStream cloneInputStream(ServletInputStream inputStream){
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		try {
			while((length = inputStream.read(buffer)) > -1){
				byteArrayOutputStream.write(buffer, 0 , length);
			}
			byteArrayOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		return byteArrayInputStream;
	}
}
