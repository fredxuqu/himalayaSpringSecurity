package com.himalaya.auth.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.himalaya.auth.util.IOUtils;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年9月20日 下午2:55:15
* Description
*/
@Component
public class LoggingFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(SDKAccessDecisionManager.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// get http request
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        BodyHttpServletRequestWrapper servletRequestWrapper = new BodyHttpServletRequestWrapper(httpServletRequest);
        BodyHttpServletResponseWrapper httpServletResponseWrapper = new BodyHttpServletResponseWrapper(httpServletResponse);
        
        getAttributeMap(httpServletRequest);
		LOGGER.info("LoggingFilter.doFilter() enter");
		chain.doFilter(servletRequestWrapper, httpServletResponseWrapper);
		
		String result = httpServletResponseWrapper.getResponseBody(httpServletResponse.getCharacterEncoding());
		response.getOutputStream().write(result.getBytes());
		
		LOGGER.info("Result : " + result);
		
		LOGGER.info("LoggingFilter.doFilter() exit");
	}

	@Override
	public void destroy() {
	}
	
	private Map<String, String> getAttributeMap(HttpServletRequest httpRequest) {
        Map<String, String> paramMap = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpRequest.getInputStream()));
            String content = IOUtils.read(reader);
            if(StringUtils.isEmpty(content)){
                return null;
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Content : " + content);
            }
            JSONObject jsonObject = JSONObject.parseObject(content);

            // Convert JSON Object to Map
            Map<String,Object> jsonMap = (Map<String,Object>)jsonObject;
            if(jsonMap.isEmpty()){
                return null;
            }

            // Convert Object value to String value.
            paramMap = new HashMap<>();
            Iterator<String> iterator = jsonMap.keySet().iterator();
            while(iterator.hasNext()){
                String key = iterator.next();
                paramMap.put(key, String.valueOf(jsonMap.get(key)));
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Content Map : " + paramMap);
            }
        } catch(Exception e){
            LOGGER.error("Get request body failed " + e.getMessage());
        }
        return paramMap;
    }
}
