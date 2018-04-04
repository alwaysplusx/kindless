package com.harmony.kindless.jwt;

import com.harmony.kindless.jwt.JwtToken;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxii@foxmail.com
 */
public interface JwtTokenFinder {

    /**
     * 从http请求中查找出对应的jwt token
     * 
     * @param request
     *            http request
     * @return jwt token
     */
    JwtToken find(HttpServletRequest request);

}
