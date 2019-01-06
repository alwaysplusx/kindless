package com.harmony.kindless.security.jwt;

import org.springframework.security.core.AuthenticationException;

/**
 * @author wuxii
 */
public class JwtTokenException extends AuthenticationException {

    public JwtTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtTokenException(String msg) {
        super(msg);
    }

}
