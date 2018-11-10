package com.himalaya.auth.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年9月10日 上午10:51:57
* Description
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleMapperTest {
	
	@Autowired
	RoleMapper roleMapper;
		
	@Test
	public void testListRoleByAppId(){
		List<String> list = roleMapper.listRoleNameByAppKey("GUEST");
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void testListRoleNameByPermissionId(){
		List<String> list = roleMapper.listRoleNameByPermissionId(10l);
		Assert.assertEquals(2, list.size());
	}
}
