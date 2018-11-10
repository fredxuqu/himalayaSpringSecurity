package com.himalaya.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年1月16日 下午3:41:24
* Description
*/
@Component
@ConfigurationProperties(prefix = "spring.druid.datasource.web-stat-filter")
@PropertySource("classpath:druid-config.properties")
public class DruidWebStatFilterSetting {
	
	private boolean enabled;
	private String urlPattern;
	private String exclusions;
	private boolean sessionStatEnable;
	private int sessionStatMaxCount;
	private String principalSessionSame;
	private String principalCookieName;
	private boolean profileEnable;
	
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
	public String getExclusions() {
		return exclusions;
	}
	public void setExclusions(String exclusions) {
		this.exclusions = exclusions;
	}
	public boolean isSessionStatEnable() {
		return sessionStatEnable;
	}
	public void setSessionStatEnable(boolean sessionStatEnable) {
		this.sessionStatEnable = sessionStatEnable;
	}
	public int getSessionStatMaxCount() {
		return sessionStatMaxCount;
	}
	public void setSessionStatMaxCount(int sessionStatMaxCount) {
		this.sessionStatMaxCount = sessionStatMaxCount;
	}
	public String getPrincipalSessionSame() {
		return principalSessionSame;
	}
	public void setPrincipalSessionSame(String principalSessionSame) {
		this.principalSessionSame = principalSessionSame;
	}
	public String getPrincipalCookieName() {
		return principalCookieName;
	}
	public void setPrincipalCookieName(String principalCookieName) {
		this.principalCookieName = principalCookieName;
	}
	public boolean isProfileEnable() {
		return profileEnable;
	}
	public void setProfileEnable(boolean profileEnable) {
		this.profileEnable = profileEnable;
	}
}
