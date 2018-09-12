package com.himalaya.auth.exception;

/**
 * SDK客户端异常
 *
 * @author VK.Gao
 * @date 2017/03/02
 */
public class SdkClientException extends RuntimeException {

    public SdkClientException(String message) {
        super(message);
    }

    public SdkClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public SdkClientException(Throwable cause) {
        super(cause);
    }

}
