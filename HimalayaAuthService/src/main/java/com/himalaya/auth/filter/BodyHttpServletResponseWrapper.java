package com.himalaya.auth.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年9月20日 上午10:29:17
* Description
*/
public class BodyHttpServletResponseWrapper extends HttpServletResponseWrapper {

	ByteArrayOutputStream byteArrayOutputStream = null;
	ServletOutputStream servletOutputStream = null;
	PrintWriter printWriter = null;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(BodyHttpServletResponseWrapper.class);
	
	public BodyHttpServletResponseWrapper(HttpServletResponse response) {
		super(response);
		byteArrayOutputStream = new ByteArrayOutputStream();
		servletOutputStream = new ServletOutputStreamWrapper(byteArrayOutputStream);
		printWriter = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream, Charset.forName("UTF-8")));
	}
	
	@Override
    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutputStream;
    }
	
    @Override
    public PrintWriter getWriter() throws IOException {
    	return printWriter;
    }
    
    @Override
    public void flushBuffer() throws IOException {
    	if(servletOutputStream!=null){
    		servletOutputStream.flush();
    	}
    	if(printWriter!=null){
    		printWriter.flush();
    	}
    }
    
    @Override
    public void reset() {
    	byteArrayOutputStream.reset();
    }

    /**
     * get response body
     * @return
     * @throws IOException
     */
	public String getResponseBody(String charset) throws IOException{
		flushBuffer();
		byte[] bytes = byteArrayOutputStream.toByteArray();
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Response Body " + (new String(bytes, Charset.forName(charset))));
		}
		return new String(bytes, Charset.forName(charset));
	}
	
	private class ServletOutputStreamWrapper extends ServletOutputStream {

		ByteArrayOutputStream byteArrayOutputStream = null;
		
		public ServletOutputStreamWrapper(ByteArrayOutputStream byteArrayOutputStream) {
			super();
			this.byteArrayOutputStream = byteArrayOutputStream;
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setWriteListener(WriteListener listener) {
		}

		@Override
		public void write(int b) throws IOException {
			byteArrayOutputStream.write(b);
		}		
	}
}
