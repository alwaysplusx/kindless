package com.harmony.kindless.jwt;

import com.harmony.kindless.jwt.JwtToken;

/**
 * jwt token验证器
 *
 * @author wuxii@foxmail.com
 */
public interface JwtTokenVerifier {

    /**
     * 验证jwt token是否有效, 验证内容包含
     * <ul>
     * <li>超时
     * <li>签名
     * <li>源
     * </ul>
     *
     * @param token json web token
     * @return true is valid, false is invalid
     */
    boolean verify(JwtToken token);

}
