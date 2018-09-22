package com.himalaya.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年9月7日 下午4:27:20
* Description
*/
@RestController
public class BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
	
	@RequestMapping("/")
	public String index(){
		LOGGER.debug("Auth server is running...");
		return "Auth server is running...";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin")
	public String admin(){
		LOGGER.debug("Auth Admin");
		return "Auth Admin";
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping("/user")
	public String user(){
		LOGGER.debug("Auth User");
		return "Auth User";
	}
}

class User{

	private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
