package com.himalaya.auth.service.impl;

import com.himalaya.auth.dto.AuthDTO;
import com.himalaya.auth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年9月10日 上午10:08:35
* Description
*/
@Component
public class AuthUserDetailServiceImpl implements UserDetailsService{

	@Autowired
	AuthenticationService authenticationService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (StringUtils.isEmpty(username)){
			throw new IllegalArgumentException("AppId can't be null!");
		}

		AuthDTO authDTO = authenticationService.getUserAuthenticationInfoByAppKey(username);
		if(authDTO == null){
			return null;
		}
		List<String> roleNameList = authDTO.getRoleList();
		List<GrantedAuthority> authorities = null;
		if(roleNameList!=null && !roleNameList.isEmpty()){
			authorities = new ArrayList<GrantedAuthority>();
			for (String roleName : roleNameList) {
				authorities.add(new SimpleGrantedAuthority(roleName));
			}
		}
		UserDetails userDetail = new User(authDTO.getAppKey(), authDTO.getAppSecret(), authorities);
		return userDetail;
	}
}
