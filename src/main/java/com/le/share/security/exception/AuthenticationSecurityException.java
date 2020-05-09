package com.le.share.security.exception;

/**
 * Created by yinbohe.
 * Date 2020/5/4
 * Description
 */
public class AuthenticationSecurityException extends RuntimeException {

    /**
     * 状态码
     */
    private Integer status;
    /**
     * 异常信息
     */
    private String message;

    public AuthenticationSecurityException(String message) {
        super(message);
    }

    public AuthenticationSecurityException(Integer status, String message) {
        super(message);
        this.status = status;
    }

}
