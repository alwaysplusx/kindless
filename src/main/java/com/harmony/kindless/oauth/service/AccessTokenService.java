package com.harmony.kindless.oauth.service;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;

import com.harmony.kindless.core.domain.ClientInfo;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii@foxmail.com
 */
public interface AccessTokenService extends Service<AccessToken, String> {

    /**
     * 根据accessToken查找数据
     * 
     * @param accessToken
     *            access token
     * @return access token
     */
    AccessToken findByAccessToken(String accessToken);

    /**
     * 根据提供的scope code来授予access token
     * 
     * @param sc
     *            scope token
     * @return access token
     * @throws OAuthProblemException
     */
    AccessToken grant(ScopeCode sc) throws OAuthProblemException;

    /**
     * 根据提供的client info来授予access token
     * 
     * @param ci
     *            client info
     * @return access token
     * @throws OAuthProblemException
     */
    AccessToken grant(ClientInfo ci) throws OAuthProblemException;

}
