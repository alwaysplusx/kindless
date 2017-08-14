package com.harmony.kindless.shiro.jwt;

import org.apache.shiro.ShiroException;

/**
 * @author wuxii@foxmail.com
 */
public class JwtException extends ShiroException {

    private static final long serialVersionUID = -952920357341008914L;

    public JwtException() {
        super();
    }

    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtException(String message) {
        super(message);
    }

    public JwtException(Throwable cause) {
        super(cause);
    }

}
