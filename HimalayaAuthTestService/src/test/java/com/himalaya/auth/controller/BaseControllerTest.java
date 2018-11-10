package com.himalaya.auth.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年9月7日 下午5:41:10
* Description
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BaseControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void testIndex() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk());
	}
	
	@Test
	public void testAdmin() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/admin")).andExpect(status().is3xxRedirection());
	}
	
	@Test
	@WithMockUser(username="admin", password="123456", roles={"ADMIN","USER"})
	public void testAuth() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/admin")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username="demo", password="demo", roles={"USER"})
	public void testUser() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(status().isOk());
	}
}
