package com.harmony.kindless.oauth.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.core.domain.Certificate;
import com.harmony.kindless.core.domain.ClientInfo;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.support.SecurityService;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.kindless.oauth.repository.AccessTokenRepository;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class AccessTokenServiceImpl extends ServiceSupport<AccessToken, String> implements AccessTokenService {

    private int refreshTokenExpiresSeconds = 60 * 60 * 24;

    private int tokenLength = 64;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private SecurityService securityService;

    @Override
    public AccessToken findByAccessToken(String accessToken) {
        return queryWith().equal("jwtToken.token", accessToken).getSingleResult();
    }

    @Override
    public AccessToken grant(ScopeCode sc) throws OAuthProblemException {
        if (sc.isExpired()) {
            throw OAuthProblemException.error("code expired");
        }
        User resourceOwner = sc.getUser();
        ClientInfo thirdPart = sc.getClientInfo();

        Certificate certificate = securityService.grant(resourceOwner, thirdPart, null);

        AccessToken accessToken = new AccessToken();
        accessToken.setGrantType(GrantType.AUTHORIZATION_CODE.toString());
        accessToken.setCertificate(certificate);
        accessToken.setScopeCode(sc);
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
        if (ci.isExpired()) {
            throw OAuthProblemException.error("client secret expired");
        }
        Certificate certificate = securityService.grant(ci, null);
        AccessToken accessToken = new AccessToken();
        accessToken.setGrantType(GrantType.CLIENT_CREDENTIALS.toString());
        accessToken.setCertificate(certificate);
        return saveOrUpdate(accessToken);
    }

    protected String generateToken() {
        return RandomStringUtils.randomAlphanumeric(tokenLength);
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
