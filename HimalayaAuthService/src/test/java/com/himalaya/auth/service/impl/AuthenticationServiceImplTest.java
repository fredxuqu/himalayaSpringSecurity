package com.himalaya.auth.service.impl;

import com.himalaya.auth.service.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by xuqu on 2018/9/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationServiceImplTest {

    @Autowired
    AuthenticationService authenticationService;

    @Test
    public void testGetUserAuthenticationInfoByAppKeyGuest(){

        authenticationService.getUserAuthenticationInfoByAppKey("GUEST");
    }
    
    @Test
    public void testGetUserAuthenticationInfoByAppKeyAdmin(){

        authenticationService.getUserAuthenticationInfoByAppKey("admin");
    }
}
