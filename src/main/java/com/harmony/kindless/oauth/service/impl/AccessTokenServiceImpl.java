package com.harmony.kindless.oauth.service.impl;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.core.domain.Token;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.service.SecurityService;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.kindless.oauth.repository.AccessTokenRepository;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.shiro.JwtToken.ThridpartPrincipal;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class AccessTokenServiceImpl extends ServiceSupport<AccessToken, String> implements AccessTokenService {

    private int expiresSeconds = 60 * 60 * 2;
    private int refreshTokenExpiresSeconds = 60 * 60 * 24;

    private int tokenLength = 64;

    @Autowired
    private AccessTokenRepository accessTokenRepository;
    @Autowired
    private SecurityService securityService;

    @Override
    public AccessToken grant(ScopeCode sc) throws OAuthProblemException {
        return grant(sc, getDefaultGrantProperties());
    }

    @Override
    public AccessToken grant(ScopeCode sc, GrantProperties grantProperties) throws OAuthProblemException {
        if (sc.isExpired()) {
            throw OAuthProblemException.error("code expired");
        }

        if (grantProperties == null) {
            grantProperties = getDefaultGrantProperties();
        }

        ThridpartPrincipal tpp = createThirdpartPrincipal(sc);
        Token jwtToken = securityService.grant(tpp, grantProperties.getExpiresSeconds(), grantProperties.getOriginClaims());

        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(jwtToken.getToken());
        accessToken.setGrantType(GrantType.AUTHORIZATION_CODE.toString());
        accessToken.setRefreshToken(generateToken());
        accessToken.setRefreshTime(new Date());
        accessToken.setExpiresIn(grantProperties.getExpiresSeconds());
        accessToken.setRefreshTokenExpiresIn(grantProperties.getRefreshTokenExpiresSeconds());
        accessToken.setScope(sc.getScopes());
        accessToken.setUser(sc.getUserId());
        accessToken.setClientInfo(sc.getClientId());
        return accessTokenRepository.save(accessToken);
    }

    /**
     * 平台为客户端授权
     * 
     * @param ci
     *            客户端
     * @return 平台最终授权码
     * @throws OAuthProblemException
     */
    public AccessToken grant(ClientInfo ci) throws OAuthProblemException {
        return grant(ci, getDefaultGrantProperties());
    }

    @Override
    public AccessToken grant(ClientInfo ci, GrantProperties grantProperties) throws OAuthProblemException {
        if (ci.isExpired()) {
            throw OAuthProblemException.error("client secret expired");
        }
        if (grantProperties == null) {
            grantProperties = getDefaultGrantProperties();
        }
        ThridpartPrincipal tpp = createThirdpartPrincipal(ci);
        Token jwtToken = securityService.grant(tpp, grantProperties.getExpiresSeconds(), grantProperties.getOriginClaims());

        AccessToken accessToken = new AccessToken();
        accessToken.setGrantType(GrantType.CLIENT_CREDENTIALS.toString());
        accessToken.setAccessToken(jwtToken.getToken());
        accessToken.setRefreshToken(generateToken());
        accessToken.setClientInfo(ci.getClientId());
        accessToken.setRefreshTime(new Date());
        accessToken.setExpiresIn(grantProperties.getExpiresSeconds());
        accessToken.setRefreshTokenExpiresIn(grantProperties.getRefreshTokenExpiresSeconds());
        return saveOrUpdate(accessToken);
    }

    protected GrantProperties getDefaultGrantProperties() {
        return new GrantProperties(expiresSeconds, refreshTokenExpiresSeconds);
    }

    /**
     * 根据客户端信息系统生成第三方账号信息
     * 
     * @param clientInfo
     *            客户端信息
     * @return tpp
     */
    private ThridpartPrincipal createThirdpartPrincipal(ClientInfo clientInfo) {
        User user = clientInfo.getVirtualUser();
        return new ThridpartPrincipal(user.getUsername(), clientInfo.getClientId(), null);
    }

    /**
     * 根据用户授予的scope code创建第三方账号信息
     * 
     * @param sc
     *            scope code
     * @return tpp
     */
    protected ThridpartPrincipal createThirdpartPrincipal(ScopeCode sc) {
        return new ThridpartPrincipal(sc.getUsername(), sc.getClientId(), sc.getCode());
    }

    protected String generateToken() {
        return RandomStringUtils.randomAlphanumeric(tokenLength);
    }

    public int getExpiresSeconds() {
        return expiresSeconds;
    }

    public void setExpiresSeconds(int expiresSeconds) {
        this.expiresSeconds = expiresSeconds;
    }

    public int getRefreshTokenExpiresSeconds() {
        return refreshTokenExpiresSeconds;
    }

    public void setRefreshTokenExpiresSeconds(int refreshTokenExpiresSeconds) {
        this.refreshTokenExpiresSeconds = refreshTokenExpiresSeconds;
    }

    public int getTokenLength() {
        return tokenLength;
    }

    public void setTokenLength(int tokenLength) {
        this.tokenLength = tokenLength;
    }

    @Override
    protected QueryableRepository<AccessToken, String> getRepository() {
        return accessTokenRepository;
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
