package com.himalaya.auth.enums;

import com.himalaya.auth.constant.HttpConstant;

/**
 * HTTP METHOD ENUMS
 *
 * @author VK.Gao
 * @date 2017/03/02
 */
public enum Method {
    /**
     * httpGet
     */
    GET("GET", HttpConstant.CLOUDAPI_CONTENT_TYPE_FORM, HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON),

    /**
     * httpPost with form
     */
    POST_FORM("POST", HttpConstant.CLOUDAPI_CONTENT_TYPE_FORM, HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON),

    /**
     * httpPost with binary body
     */
    POST_BODY("POST", HttpConstant.CLOUDAPI_CONTENT_TYPE_STREAM, HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON),

    /**
     * httpPut with form
     */
    PUT_FORM("PUT", HttpConstant.CLOUDAPI_CONTENT_TYPE_FORM, HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON),

    /**
     * httpPut with binary body
     */
    PUT_BODY("PUT", HttpConstant.CLOUDAPI_CONTENT_TYPE_STREAM, HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON),

    /**
     * httpPatch with form
     */
    PATCH_FORM("PATCH", HttpConstant.CLOUDAPI_CONTENT_TYPE_FORM, HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON),

    /**
     * httpPatch with binary body
     */
    PATCH_BODY("PATCH", HttpConstant.CLOUDAPI_CONTENT_TYPE_STREAM, HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON),

    /**
     * httpDelete
     */
    DELETE("DELETE", HttpConstant.CLOUDAPI_CONTENT_TYPE_FORM, HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON),

    /**
     * httpHead
     */
    HEAD("HEAD", HttpConstant.CLOUDAPI_CONTENT_TYPE_FORM, HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON);

    private String name;
    private String requestContentType;
    private String acceptContentType;

    Method(String name, String requestContentType, String acceptContentType) {
        this.name = name;
        this.requestContentType = requestContentType;
        this.acceptContentType = acceptContentType;
    }

    public String getName() {
        return name;
    }

    public String getRequestContentType() {
        return requestContentType;
    }

    public String getAcceptContentType() {
        return acceptContentType;
    }
}
