package com.harmony.kindless.oauth.service;

import java.util.Date;
import java.util.UUID;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.kindless.oauth.repository.AccessTokenRepository;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class AccessTokenService extends ServiceSupport<AccessToken, String> {

    private int expiresSeconds = 60 * 60 * 2;
    private int refreshTokenExpiresSeconds = 60 * 60 * 24;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Override
    protected QueryableRepository<AccessToken, String> getRepository() {
        return accessTokenRepository;
    }

    /**
     * 平台为客户端授权
     * 
     * @param clientInfo
     *            客户端
     * @return 平台最终授权码
     * @throws OAuthProblemException
     */
    public AccessToken grant(ClientInfo clientInfo) throws OAuthProblemException {
        if (clientInfo.isExpired()) {
            throw OAuthProblemException.error("client secret expired");
        }
        AccessToken accessToken = new AccessToken();
        accessToken.setGrantType(GrantType.CLIENT_CREDENTIALS.toString());
        accessToken.setAccessToken(UUID.randomUUID().toString());
        accessToken.setRefreshToken(UUID.randomUUID().toString());
        accessToken.setClientId(clientInfo.getClientId());
        accessToken.setRefreshTime(new Date());
        accessToken.setExpiresIn(expiresSeconds);
        accessToken.setRefreshTokenExpiresIn(refreshTokenExpiresSeconds);
        return saveOrUpdate(accessToken);
    }

    /**
     * 用户为第三方授权
     * 
     * @param scopeCode
     *            用户授权的临时码
     * @return 用户最终授权码
     * @throws OAuthProblemException
     */
    public AccessToken grant(ScopeCode scopeCode) throws OAuthProblemException {
        if (scopeCode.isExpired()) {
            throw OAuthProblemException.error("code expired");
        }
        AccessToken accessToken = new AccessToken();
        accessToken.setGrantType(GrantType.AUTHORIZATION_CODE.toString());
        accessToken.setAccessToken(UUID.randomUUID().toString());
        accessToken.setRefreshToken(UUID.randomUUID().toString());
        accessToken.setRefreshTime(new Date());
        accessToken.setExpiresIn(expiresSeconds);
        accessToken.setRefreshTokenExpiresIn(refreshTokenExpiresSeconds);
        accessToken.setScope(scopeCode.getScopes());
        accessToken.setUsername(scopeCode.getUsername());
        accessToken.setClientId(scopeCode.getClientId());
        return accessTokenRepository.save(accessToken);
    }

    /**
     * 用户给第三方授权
     * 
     * @param user
     *            用户
     * @param clientInfo
     *            第三方
     * @return 授权码
     * @throws OAuthProblemException
     */
    public AccessToken grant(User user, ClientInfo clientInfo, GrantType grantType) throws OAuthProblemException {
        if (clientInfo.isExpired()) {
            throw OAuthProblemException.error("client secret expired");
        }
        AccessToken accessToken = new AccessToken();
        accessToken.setGrantType(grantType.toString());
        accessToken.setAccessToken(UUID.randomUUID().toString());
        accessToken.setRefreshToken(UUID.randomUUID().toString());
        accessToken.setRefreshTime(new Date());
        accessToken.setExpiresIn(expiresSeconds);
        accessToken.setRefreshTokenExpiresIn(refreshTokenExpiresSeconds);
        accessToken.setScope("all");
        accessToken.setUsername(user.getUsername());
        accessToken.setClientId(clientInfo.getClientId());
        return accessTokenRepository.save(accessToken);
    }

    /**
     * 使用原来的授权码刷新得到新的授权码
     * 
     * @param accessToken
     *            原授权码
     * @return 刷新后的授权码
     * @throws OAuthProblemException
     */
    public AccessToken refresh(AccessToken accessToken) throws OAuthProblemException {
        if (accessToken.isRefreshTokenExpired()) {
            throw OAuthProblemException.error("refresh_token expired");
        }
        return null;
    }

}
