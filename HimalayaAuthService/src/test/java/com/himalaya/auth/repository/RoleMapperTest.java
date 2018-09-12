package com.himalaya.auth.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.himalaya.auth.domain.RoleDO;

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
		List<RoleDO> list = roleMapper.listRoleByAppId("GUEST");
		Assert.assertEquals(1, list.size());
	}
}
