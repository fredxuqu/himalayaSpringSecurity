package com.himalaya.auth.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.himalaya.auth.domain.UserDO;

import org.junit.Assert;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年9月10日 上午10:51:57
* Description
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
	
	@Autowired
	UserMapper userMapper;

	@Test
	public void testSelectByPrimaryKey(){
		UserDO userDO = userMapper.selectByPrimaryKey(1003l);
		Assert.assertEquals("GUEST", userDO.getAppId());
	}

	@Test
	public void testSelectUserByAppId(){
		UserDO userDO = userMapper.selectUserByAppId("GUEST");
		Assert.assertEquals("GUEST", userDO.getAppId());
	}
}
