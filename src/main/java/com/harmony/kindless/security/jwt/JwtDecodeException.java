package com.harmony.kindless.security.jwt;

/**
 * @author wuxii
 */
public class JwtDecodeException extends RuntimeException {

    public JwtDecodeException() {
        super();
    }

    public JwtDecodeException(String message) {
        super(message);
    }

    public JwtDecodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtDecodeException(Throwable cause) {
        super(cause);
    }

}
