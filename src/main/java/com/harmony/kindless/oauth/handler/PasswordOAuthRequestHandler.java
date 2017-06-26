package com.harmony.kindless.oauth.handler;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.repository.UserRepository;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.service.AccessTokenService;

/**
 * @author wuxii@foxmail.com
 */
public class PasswordOAuthRequestHandler extends AbstractOAuthRequestHandler {

    private UserRepository userRepository;
    private AccessTokenService accessTokenService;

    @Override
    public GrantType getSupportedGrantType() {
        return GrantType.PASSWORD;
    }

    @Override
    public OAuthResponse handle(OAuthRequest request) throws OAuthProblemException {
        // UserValidator
        AccessToken accessToken = accessTokenService.createAccessToken(new User(), null);
        try {
            return OAuthASResponse//
                    .tokenResponse(HttpServletResponse.SC_OK)//
                    .setAccessToken(accessToken.getAccessToken())//
                    .setExpiresIn(String.valueOf(accessToken.getExpiresIn()))//
                    .setRefreshToken(accessToken.getRefreshToken())//
                    .buildJSONMessage();
        } catch (OAuthSystemException e) {
            return null;
        }
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
