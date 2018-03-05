package com.harmony.kindless.core.service;

import com.harmony.kindless.core.domain.Token;
import com.harmony.kindless.shiro.JwtToken;
import com.harmony.kindless.shiro.JwtTokenVerifier;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii@foxmail.com
 */
public interface TokenService extends Service<Token, String>, JwtTokenVerifier {

    /**
     * 获取token相关的session id
     * 
     * @param token
     *            jwt token
     * @return session id
     */
    String getSessionId(JwtToken token);

    /**
     * 验证token的签名
     * 
     * @param token
     *            jwt token
     * @return true 签名正确, false签名错误
     */
    boolean verifySignature(JwtToken token);

    /**
     * 验证token的源信息
     * 
     * @param token
     *            jwt token
     * @return true原与目标token一致, false与目标token不一直
     */
    boolean verifyOrigin(JwtToken token);

}
