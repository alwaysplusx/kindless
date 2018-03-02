package com.harmony.kindless.oauth.handler;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;

import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.service.UserService;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.oauth.service.ClientInfoService;

/**
 * @author wuxii@foxmail.com
 */
public class PasswordOAuthRequestHandler extends AbstractOAuthRequestHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private ClientInfoService clientInfoService;
    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    public GrantType getSupportedGrantType() {
        return GrantType.PASSWORD;
    }

    @Override
    public OAuthResponse handle(OAuthRequest request) throws OAuthProblemException, OAuthSystemException {
        String clientId = request.getClientId();
        String clientSecret = request.getClientSecret();
        String username = request.getParam("username");
        String password = request.getParam("password");

        ClientInfo clientInfo = clientInfoService.findOne(clientId);
        if (clientInfo == null || !clientInfo.getClientSecret().equals(clientSecret)) {
            throw OAuthProblemException.error("invalid client_id or client_secret");
        }

        User user = userService.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw OAuthProblemException.error("invalid username or password");
        }

        AccessToken accessToken = accessTokenService.grant(user, clientInfo, getSupportedGrantType());

        return OAuthASResponse//
                .tokenResponse(HttpServletResponse.SC_OK)//
                .setAccessToken(accessToken.getAccessToken())//
                .setExpiresIn(String.valueOf(accessToken.getExpiresIn()))//
                .setRefreshToken(accessToken.getRefreshToken())//
                .buildJSONMessage();
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public AccessTokenService getAccessTokenService() {
        return accessTokenService;
    }

    public void setAccessTokenService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    public ClientInfoService getClientInfoService() {
        return clientInfoService;
    }

    public void setClientInfoService(ClientInfoService clientInfoService) {
        this.clientInfoService = clientInfoService;
    }

}
