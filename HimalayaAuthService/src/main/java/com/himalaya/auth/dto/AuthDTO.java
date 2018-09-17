package com.himalaya.auth.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuqu on 2018/9/14.
 */
public class AuthDTO implements Serializable{

    private static final long serialVersionUID = -2446660219795065473L;

    private String appKey;
    private String appSecret;
    private List<String> roleList;

    public AuthDTO() {
    }

    public AuthDTO(String appKey, String appSecret, List<String> roleList) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.roleList = roleList;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }
}
