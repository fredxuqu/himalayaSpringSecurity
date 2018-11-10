package com.himalaya.auth.service;

import com.himalaya.auth.domain.RoleDO;
import com.himalaya.auth.domain.UserDO;
import com.himalaya.auth.repository.RoleMapper;
import com.himalaya.auth.repository.UserMapper;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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
	UserMapper userMapper;
	
	@Autowired
	RoleMapper roleMapper;
	
/*	@Override 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (StringUtils.isBlank(username)){
			throw new IllegalArgumentException("AppId can't be null!");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		UserDetails userDetail = new User("admin", "123456", authorities);
		return userDetail;
	}*/
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (StringUtils.isEmpty(username)){
			throw new IllegalArgumentException("AppId can't be null!");
		}
		// TODO
		// get UserDetails from redis

		// if not exists in redis then get from database

			// mapping username == appid
			// password == appsecret
			// get username and password(appsecret) by appid
			UserDO userDO = userMapper.selectUserByAppId(username);
			if(userDO == null){
				throw new UsernameNotFoundException("User can't be found in system.");
			}

			// get authorities(role)
			List<RoleDO> roleList = roleMapper.listRoleByAppId(username);

			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			for (RoleDO roleDO : roleList) {
				authorities.add(new SimpleGrantedAuthority(roleDO.getRoleName()));
			}

			UserDetails userDetail = new User(userDO.getAppId(), userDO.getAppSecret(), authorities);

			// put the userDetails to redis server

		return userDetail;
	}
}
