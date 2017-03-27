package com.harmony.kindless.oauth.service;

import org.springframework.stereotype.Service;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ClientInfo;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class AccessTokenService {

    /**
     * 用户给第三方授权
     * 
     * @param code
     *            用户所确认的code
     * @param clientInfo
     *            第三方
     * @return accessToken
     */
    public AccessToken createAccessToken(String code, ClientInfo clientInfo) {
        return null;
    }

    /**
     * 直接给第三方授权(第三方给自己授权)
     * 
     * @param clientInfo
     *            第三方
     * @return accessToken
     */
    public AccessToken createAccessToken(ClientInfo clientInfo) {
        return null;
    }

    public AccessToken createAccessToken(User user, ClientInfo clientInfo) {
        return null;
    }

    public AccessToken refreshToken(String refreshToken) {
        return null;
    }

}
