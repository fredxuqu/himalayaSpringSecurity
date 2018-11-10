package com.himalaya.auth.util;

import com.himalaya.auth.constant.SdkConstant;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuqu on 2018/9/12.
 */
public class SignUtilTest {

    @Test
    public void test(){
        String method = "GET";
        String appKey = "appkey1001";
        String appSecret = "nHfBp45H3nt/y2CwiTFocyNkDTp3FuOzKKkEcbv50Dc=";
        String version = "1.0";
        String timestamp = String.valueOf(System.currentTimeMillis());

        Map<String, String> headerParams = new HashMap<String, String>();
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_KEY, appKey);
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_VERSION, version);
        headerParams.put(SdkConstant.CLOUDAPI_X_CA_TIMESTAMP, timestamp);

        String pathWithParams = "http://127.0.0.1:8080/auth/index";

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("flag", "111211");
        Map<String, String> formParams = null;
        String sign = SignUtil.sign(method, appSecret, headerParams, pathWithParams, queryParams, formParams);

        System.out.println(sign);
        
//        nHfBp45H3nt/y2CwiTFocyNkDTp3FuOzKKkEcbv50Dc=
//        K6YyxWt8cJitTN4aGRG5DEUpzM66yQrdJyBxDXdMPLM=
    }
}
