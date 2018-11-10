package com.himalaya.auth.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.himalaya.auth.config.RedisConfig;
import com.himalaya.auth.constants.SysConstant;
import com.himalaya.auth.domain.PermissionDO;
import com.himalaya.auth.repository.PermissionMapper;
import com.himalaya.auth.repository.RoleMapper;

/**
 * Created by xuqu on 2018/9/13.
 */
@Component
public class SDKSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

	@Autowired
	RoleMapper roleMapper;

	@Autowired
	PermissionMapper permissionMapper;

	@Autowired
	RedisConfig redisConfig;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//		FilterInvocation fi = (FilterInvocation) object;
//	    String url = fi.getRequestUrl();
//
//	    String appKey = String.class.cast(
//	            HttpServletRequest.class.cast(fi.getHttpRequest()).getHeader(SdkConstant.CLOUDAPI_X_CA_KEY));

	    // get url role map by app key
	    Map<String, Collection<ConfigAttribute>> rolePermissionMap = loadRolePermissionMap();

	    //object 中包含用户请求的request 信息
	    HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
	    AntPathRequestMatcher matcher;
	    String resUrl;

	    for (Iterator<String> iterate = rolePermissionMap.keySet().iterator(); iterate.hasNext(); ){
	        resUrl = iterate.next();
	        matcher = new AntPathRequestMatcher(resUrl);
	        if(matcher.matches(request)){
	            return rolePermissionMap.get(resUrl);
	        }
	    }
	    
	    return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
	
	private Map<String, Collection<ConfigAttribute>> loadRolePermissionMap(){

		// get from redis
		Object cachedPermissionListObject = redisTemplate.opsForValue().get(SysConstant.REDIS_KEY_ALL_PERMISSIONS);
		if(cachedPermissionListObject != null){
			return convertToConfigAttributeMap((List<PermissionDO>)cachedPermissionListObject);
		} else {
			// if not exists, then fetch to database
			List<PermissionDO> permissionList = permissionMapper.getAllPermission();

			// cache to redis
			if(redisConfig.isEnabled() && !permissionList.isEmpty()) {
				LOGGER.info("AuthDTO JSON : " + JSON.toJSONString(permissionList));
				redisTemplate.opsForValue().set(SysConstant.REDIS_KEY_ALL_PERMISSIONS, permissionList,
						redisConfig.getExpireTime() != 0 ? redisConfig.getExpireTime() : 84600, TimeUnit.SECONDS);
			}
			return convertToConfigAttributeMap(permissionList);
		}
	}

	private Map<String, Collection<ConfigAttribute>> convertToConfigAttributeMap(List<PermissionDO> permissionList){
		Map<String, Collection<ConfigAttribute>> rolePermissionMap = new HashMap<>();
		for (PermissionDO permissionDO : permissionList) {
			// get rolePermissionList from database
			List<String> roleNameList = roleMapper.listRoleNameByPermissionId(permissionDO.getId());
			Collection<ConfigAttribute> cfgList = new ArrayList<>();
			for (String roleName : roleNameList) {
				cfgList.add(new SecurityConfig(roleName));
			}
			rolePermissionMap.put(permissionDO.getUrl(), cfgList);
		}
		return rolePermissionMap;
	}
}