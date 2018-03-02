package com.harmony.kindless.core.service;

import com.harmony.kindless.core.domain.Token;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.shiro.JwtToken;
import com.harmony.kindless.shiro.JwtToken.OriginClaims;
import com.harmony.kindless.shiro.ThirdPartPrincipal;
import com.harmony.kindless.shiro.TokenVerifier;

/**
 * @author wuxii@foxmail.com
 */
public interface SecurityService extends TokenVerifier {

    /**
     * 通过用户名获取用户信息
     * 
     * @param username
     *            用户名
     * @return 用户信息
     * @see UserService#findByUsername(String)
     */
    User findUser(String username);

    /**
     * 根据token获取对应的session id
     * 
     * @param token
     *            jwt token
     * @return shiro session id
     * @see TokenService#getSessionId(JwtToken)
     */
    String getSessionId(JwtToken token);

    /**
     * 用户登录并授予token
     * 
     * @param username
     *            用户账号
     * @param password
     *            密码
     * @param claims
     *            源
     * @return jwt token
     */
    Token login(String username, String password, OriginClaims claims);

    /**
     * 使用jwt token
     * 
     * @param jwtToken
     * @return
     */
    Token login(JwtToken jwtToken);

    /**
     * 授予第三方token
     * 
     * @param tpp
     *            第三方账号
     * @param claims
     *            第三方的源
     * @return jwt token
     */
    Token grant(ThirdPartPrincipal tpp, OriginClaims claims);

}
