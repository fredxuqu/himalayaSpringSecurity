package com.himalaya.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.himalaya.auth.config.RedisConfig;
import com.himalaya.auth.constants.SysConstant;
import com.himalaya.auth.domain.UserDO;
import com.himalaya.auth.dto.AuthDTO;
import com.himalaya.auth.repository.RoleMapper;
import com.himalaya.auth.repository.RolePermissionMapper;
import com.himalaya.auth.repository.UserMapper;
import com.himalaya.auth.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuqu on 2018/9/13.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    RedisConfig redisConfig;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Override
    public AuthDTO getUserAuthenticationInfoByAppKey(String appKey) {

        Object cacheObj = redisTemplate.opsForValue().get(SysConstant.REDIS_KEY_PREFIX_AUTH + appKey);

        if(cacheObj!=null && cacheObj instanceof AuthDTO){
            LOGGER.debug("Get User from redis cache.");
            AuthDTO authDTO = (AuthDTO)cacheObj;
            return authDTO;
        }

        // get user info
        UserDO userDO = userMapper.getUserByAppKey(appKey);

        // get role name list
        List<String> roleList = roleMapper.listRoleNameByAppKey(appKey);

        AuthDTO authDTO = null;
        if(userDO != null){
            authDTO = new AuthDTO(userDO.getAppKey(), userDO.getAppSecret(), roleList);
            if(redisConfig.isEnabled()) {
                LOGGER.info("AuthDTO JSON : " + JSON.toJSONString(authDTO));
                redisTemplate.opsForValue().set(SysConstant.REDIS_KEY_PREFIX_AUTH + appKey, authDTO,
                        redisConfig.getExpireTime() != 0 ? redisConfig.getExpireTime() : 84600, TimeUnit.SECONDS);
            }
        }
        return authDTO;
    }
}
