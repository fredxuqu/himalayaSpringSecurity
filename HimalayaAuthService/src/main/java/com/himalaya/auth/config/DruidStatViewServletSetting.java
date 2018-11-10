package com.himalaya.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年1月16日 下午3:42:03
* Description
*/
@Component
@ConfigurationProperties(prefix = "spring.druid.datasource.stat-view-servlet")
@PropertySource("classpath:druid-config.properties")
public class DruidStatViewServletSetting {

	private boolean enabled;
	private String urlPattern;
	private boolean resetEnable;
	private String loginUserName;
	private String loginPassword;
	private String allow;
	private String deny;
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getUrlPattern() {
		return urlPattern;
	}
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}
	public boolean isResetEnable() {
		return resetEnable;
	}
	public void setResetEnable(boolean resetEnable) {
		this.resetEnable = resetEnable;
	}
	public String getLoginUserName() {
		return loginUserName;
	}
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getAllow() {
		return allow;
	}
	public void setAllow(String allow) {
		this.allow = allow;
	}
	public String getDeny() {
		return deny;
	}
	public void setDeny(String deny) {
		this.deny = deny;
	}
}
