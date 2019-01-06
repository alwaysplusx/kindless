package com.harmony.kindless.security.jwt;

/**
 * @author wuxii
 */
public interface JwtTokenDecoder {

    JwtToken decode(String tokenValue);

}
