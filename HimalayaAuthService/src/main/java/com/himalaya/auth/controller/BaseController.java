package com.himalaya.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
	
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin")
	public String admin(@RequestParam("flag") String flag){
		LOGGER.debug("Auth Admin " + flag);
		return "Auth Admin " + flag;
	}
	
//	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping("/user")
	public String user(@RequestParam("flag") String flag){
		LOGGER.debug("Auth User " + flag);
		return "Auth User " + flag;
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(@RequestParam("flag") String flag){
		LOGGER.debug("Auth User and Admin " + flag);
		return "Auth User and Admin " + flag;
	}

	@RequestMapping(value = "/post", 
					method = { RequestMethod.POST }, 
					//params={"",""}
					//headers={"",""}
					consumes={"application/octet-stream; charset=UTF-8","application/json; charset=UTF-8"},
					produces={"application/octet-stream; charset=UTF-8","application/json; charset=UTF-8"})
	public String post(@RequestBody User user){
		LOGGER.debug("Auth User and Admin " + user.getFlag());
		return "Auth User and Admin " + user.getFlag();
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
