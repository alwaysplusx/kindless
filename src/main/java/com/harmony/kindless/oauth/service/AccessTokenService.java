package com.harmony.kindless.oauth.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.repository.UserRepository;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.kindless.oauth.repository.AccessTokenRepository;
import com.harmony.kindless.oauth.repository.ScopeCodeRepository;
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
    private ScopeCodeRepository scopeCodeRepository;
    @Autowired
    private AccessTokenRepository accessTokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected QueryableRepository<AccessToken, String> getRepository() {
        return accessTokenRepository;
    }

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
        ScopeCode scopeCode = scopeCodeRepository.findByCodeAndClientId(code, clientInfo.getClientId());
        User user = userRepository.findByUsername(scopeCode.getUsername());
        return grant(user, clientInfo, scopeCode.getScope());
    }

    /**
     * 直接给第三方授权(第三方给自己授权)
     * 
     * @param clientInfo
     *            第三方
     * @return accessToken
     */
    public AccessToken createAccessToken(ClientInfo clientInfo) {
        User user = userRepository.findByClientId(clientInfo.getClientId());
        return createAccessToken(user, clientInfo);
    }

    /**
     * 通过用户名密码给第三方授权
     * 
     * @param user
     *            用户
     * @param clientInfo
     *            第三方
     * @return accessToken
     */
    public AccessToken createAccessToken(User user, ClientInfo clientInfo) {
        AccessToken accessToken = new AccessToken();
        accessToken.setUsername(user.getUsername());
        return null;
    }

    public AccessToken refreshToken(String refreshToken, ClientInfo clientInfo) {
        AccessToken accessToken = accessTokenRepository.findByClientIdAndRefreshToken(clientInfo.getClientId(), refreshToken);
        accessToken.setActivationTime(new Date());
        return accessTokenRepository.save(accessToken);
    }

    private AccessToken grant(User user, ClientInfo clientInfo, String scope) {
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(UUID.randomUUID().toString());
        accessToken.setRefreshToken(UUID.randomUUID().toString());
        accessToken.setExpiresIn(expiresSeconds);
        accessToken.setRefreshTokenExpiresIn(refreshTokenExpiresSeconds);
        accessToken.setScope(scope);
        accessToken.setUsername(user.getUsername());
        accessToken.setClientId(clientInfo.getClientId());
        return accessTokenRepository.save(accessToken);
    }

}
