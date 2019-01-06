package com.harmony.kindless.security.jwt;

import com.harmony.kindless.security.IdentityUserDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxii
 */
public interface JwtTokenGenerator {

    /**
     * 根据用户信息生成jwt token
     *
     * @param userDetails 用户信息
     * @param request     http request
     * @return
     */
    String generate(IdentityUserDetails userDetails, HttpServletRequest request);

}
