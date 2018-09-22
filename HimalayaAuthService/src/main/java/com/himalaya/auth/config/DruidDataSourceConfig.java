package com.himalaya.auth.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

@Configuration
public class DruidDataSourceConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DruidDataSourceConfig.class);

	@Autowired
    private DruidStatViewServletSetting statViewServletSetting;

	@Autowired
    private DruidWebStatFilterSetting webStatFilter;

    @Bean
    @ConfigurationProperties("spring.druid.datasource")
    public DruidDataSource dataSource(
            DataSourceProperties properties) throws Exception{
    	
    	LOGGER.info("Initialize Druid DataSource......");
    	
        DruidDataSource dataSource = new DruidDataSource();
        LOGGER.info("Druid DataSource Initialized , " + dataSource.hashCode());
        return dataSource;
    }
    
    @Bean
    public ServletRegistrationBean druidServlet() {  
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();  
        servletRegistrationBean.setServlet(new StatViewServlet());  
        servletRegistrationBean.addUrlMappings(statViewServletSetting.getUrlPattern());  
        Map<String, String> initParameters = new HashMap<String, String>();  
        initParameters.put("loginUsername", statViewServletSetting.getLoginUserName());// 用户名  
        initParameters.put("loginPassword", statViewServletSetting.getLoginPassword());// 密码  
        initParameters.put("resetEnable", String.valueOf(statViewServletSetting.isResetEnable()));// 禁用HTML页面上的“Reset All”功能  
        initParameters.put("allow", statViewServletSetting.getAllow()); // IP白名单 (没有配置或者为空，则允许所有访问)  
        initParameters.put("deny", statViewServletSetting.getDeny());// IP黑名单 // (存在共同时，deny优先于allow)  
        servletRegistrationBean.setInitParameters(initParameters);  
        return servletRegistrationBean;  
    }
  
	@Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();  
        filterRegistrationBean.setFilter(new WebStatFilter());  
        filterRegistrationBean.addUrlPatterns(webStatFilter.getUrlPattern());  
        filterRegistrationBean.addInitParameter("exclusions", webStatFilter.getExclusions());  
        return filterRegistrationBean;  
    }
  
	// 按照BeanId来拦截配置 用来bean的监控
    @Bean(value = "druid-stat-interceptor")  
    public DruidStatInterceptor DruidStatInterceptor() {  
        DruidStatInterceptor druidStatInterceptor = new DruidStatInterceptor();  
        return druidStatInterceptor;  
    }
  
	@Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {  
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();  
        beanNameAutoProxyCreator.setProxyTargetClass(true);
        beanNameAutoProxyCreator.setInterceptorNames("druid-stat-interceptor");  
        return beanNameAutoProxyCreator;  
    }
}