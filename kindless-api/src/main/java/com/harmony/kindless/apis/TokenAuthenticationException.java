package com.harmony.kindless.apis;

import com.harmony.umbrella.web.ResponseDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

/**
 * @author wuxii
 */
public class TokenAuthenticationException extends AuthenticationException implements ResponseDetails {

    private int code;
    private HttpStatus httpStatus;

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
