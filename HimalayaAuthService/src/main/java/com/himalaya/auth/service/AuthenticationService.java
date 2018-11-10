package com.himalaya.auth.service;

import com.himalaya.auth.dto.AuthDTO;

/**
 * Created by xuqu on 2018/9/13.
 */
public interface AuthenticationService {
    /**
     * get appkey
     *     appsecret
     *     roles
     *     roleurl mapping
     *     by appkey
     * @param appKey
     * @return
     */
    AuthDTO getUserAuthenticationInfoByAppKey(String appKey);
}
