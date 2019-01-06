package com.harmony.kindless.security.jwt;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxii
 */
public interface JwtTokenExtractor {

    String extract(HttpServletRequest request);

}
