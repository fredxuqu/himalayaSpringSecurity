package com.himalaya.auth.http;

import com.himalaya.auth.HttpClient;
import com.himalaya.auth.exception.SdkClientException;
import com.himalaya.auth.model.BuilderParams;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;

/**
 * @author VK.Gao
 * @date 2017/05/15
 */
public class HttpClientFactory {

    public static String HTTP_CLIENT_IMPL_KEY = "himalaya.sdk.httpclient";
    public static String DEFAULT_HTTP_CLIENT = "com.himalaya.auth.http.ApacheHttpClient";

    public static HttpClient buildClient(BuilderParams builderParams) {
        try {
            String httpClientClassName = System.getProperty(HTTP_CLIENT_IMPL_KEY);
            if (StringUtils.isEmpty(httpClientClassName)) {
                httpClientClassName = DEFAULT_HTTP_CLIENT;
            }
            Class httpClientClass = Class.forName(httpClientClassName);
            if (!HttpClient.class.isAssignableFrom(httpClientClass)) {
                throw new IllegalStateException(String.format("%s is not assignable from com.alibaba.cloudapi.sdk.core.HttpClient", httpClientClassName));
            }
            Constructor<? extends HttpClient> constructor = httpClientClass.getConstructor(BuilderParams.class);
            return constructor.newInstance(builderParams);
        } catch (Exception e) {
            throw new SdkClientException("HttpClientFactory buildClient failed", e);
        }
    }
}
