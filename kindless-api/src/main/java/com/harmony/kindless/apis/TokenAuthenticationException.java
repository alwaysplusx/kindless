package com.harmony.kindless.apis;

import org.springframework.security.core.AuthenticationException;

import com.harmony.umbrella.web.ResponseDetails;

/**
 * @author wuxii
 */
public class TokenAuthenticationException extends AuthenticationException implements ResponseDetails {

    private static final long serialVersionUID = 5673466748136554012L;
    private int code;

    public TokenAuthenticationException(ResponseDetails r, Throwable t) {
        super(r.getMsg(), t);
        this.code = r.getCode();
    }

    public TokenAuthenticationException(ResponseDetails r) {
        this(r, null);
    }

    public TokenAuthenticationException(String msg, Throwable t) {
        super(msg, t);
        this.code = ResponseCodes.UNAUTHORIZED.code();
    }

    public TokenAuthenticationException(String msg) {
        super(msg);
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return getMessage();
    }

}
