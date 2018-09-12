package com.himalaya.auth;

import com.himalaya.auth.model.ApiCallBack;
import com.himalaya.auth.model.ApiRequest;
import com.himalaya.auth.model.ApiResponse;
import com.himalaya.auth.model.BuilderParams;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.Future;

/**
 * httpclient interface
 *
 * @author VK.Gao
 * @date 2017/03/01
 */
public abstract class HttpClient implements Closeable{

    public HttpClient(BuilderParams builderParams){
        init(builderParams);
    }

    protected abstract void init(BuilderParams builderParams);

    public abstract ApiResponse syncInvoke(ApiRequest request) throws IOException;

    public abstract Future<ApiResponse> asyncInvoke(ApiRequest request, ApiCallBack callback);

    public abstract void shutdown();

    @Override
    public void close() throws IOException {
        shutdown();
    }
}
