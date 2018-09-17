package com.himalaya.auth.config;

import com.himalaya.auth.domain.UserDO;
import com.himalaya.auth.dto.AuthDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuqu on 2018/9/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigTest {

    @Autowired
    @Qualifier("redisTemplateString")
    RedisTemplate<String, Object> redisTemplateString;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testSet(){
        redisTemplateString.opsForValue().set("key1","00010101010");

        Object cachedValue = redisTemplateString.opsForValue().get("key1");

        System.out.println(cachedValue.toString());
    }

    @Test
    public void testSetMap(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1","sldfjalsfjla");

        redisTemplate.opsForValue().set("map11", map);

        Object cachedValue = redisTemplate.opsForValue().get("map11");

        Map<String, String> cachedMap = (Map<String, String>)cachedValue;
        System.out.println(cachedValue.toString());
    }

    @Test
    public void testSetObject(){
        UserDO userDO = new UserDO();
        userDO.setId(1l);
        userDO.setAppKey("foo");
        userDO.setAppSecret("AAASSSDFE");

        redisTemplate.opsForValue().set("foo11", userDO);

        Object cachedValue = redisTemplate.opsForValue().get("foo11");

        UserDO cachedObj = (UserDO)cachedValue;
        System.out.println(cachedObj.getAppKey());
    }

    @Test
    public void testSetMap1(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1","sldfjalsfjla");

        redisTemplate.opsForValue().set("map2", map);

        Object cachedValue = redisTemplate.opsForValue().get("map2");

        Map<String, String> cachedMap = (Map<String, String>)cachedValue;
        System.out.println(cachedValue.toString());
    }

    @Test
    public void testSetObject1(){
        UserDO userDO = new UserDO();
        userDO.setId(1l);
        userDO.setAppKey("foo");
        userDO.setAppSecret("AAASSSDFE");

        redisTemplate.opsForValue().set("foo2",userDO);

        Object cachedValue = redisTemplate.opsForValue().get("foo2");

        UserDO cachedObj = (UserDO)cachedValue;
        System.out.println(cachedObj.getAppKey());
    }
    
    @Test
    public void testGetNull(){
        
        Object cachedValue = redisTemplate.opsForValue().get("xxxxx");        
        Assert.assertNull(cachedValue);
    }
    
    @Test
    public void testSetObject2(){
        AuthDTO authDTO = new AuthDTO();
        authDTO.setAppKey("GUEST");
        authDTO.setAppSecret("123456");
        authDTO.setRoleList(new ArrayList<>());
        List list = new ArrayList<>();
        list.add("ROLE_SEARCH");
        authDTO.setRoleList(list);

        redisTemplate.opsForValue().set("foo2",authDTO);
        Object cachedValue = redisTemplate.opsForValue().get("foo2");
        
        System.out.println(cachedValue.toString());
    }
}