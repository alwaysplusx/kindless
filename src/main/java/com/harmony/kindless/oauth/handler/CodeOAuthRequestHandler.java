package com.harmony.kindless.oauth.handler;

import java.util.Set;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.OAuthResponse.OAuthResponseBuilder;

import com.harmony.kindless.oauth.OAuthRequestHandler;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.kindless.oauth.service.ClientInfoService;
import com.harmony.kindless.oauth.service.ScopeCodeService;

/**
 * @author wuxii@foxmail.com
 */
public class CodeOAuthRequestHandler implements OAuthRequestHandler {

    private ClientInfoService clientInfoService;

    private ScopeCodeService scopeCodeService;

    public boolean support(OAuthRequest request) {
        String responseType = request.getParam(OAuth.OAUTH_RESPONSE_TYPE);
        return OAuth.OAUTH_CODE.equals(responseType);
    }

    public OAuthResponse handle(OAuthRequest request) throws OAuthProblemException, OAuthSystemException {
        String clientId = request.getClientId();
        String state = request.getParam("state");
        Set<String> scopes = request.getScopes();
        // FIXME 设置当前登录用户
        String username = "wuxii";

        ClientInfo clientInfo = clientInfoService.findOne(clientId);
        if (clientInfo == null) {
            throw OAuthProblemException.error("invalid client id " + clientId)//
                    .responseStatus(403);
        }
        String redirectURI = request.getRedirectURI();
        if (!clientInfo.getRedirectUri().equals(redirectURI)) {
            throw OAuthProblemException.error("invalid reddirect uri " + redirectURI)//
                    .responseStatus(403);
        }

        ScopeCode code = scopeCodeService.grant(username, clientId, scopes);

        OAuthResponseBuilder builder = OAuthResponse//
                .status(302)//
                .location(redirectURI)//
                .setParam("code", code.getCode());

        if (state != null) {
            builder.setParam("state", state);
        }

        return builder.buildQueryMessage();
    }

    public ClientInfoService getClientInfoService() {
        return clientInfoService;
    }

    public void setClientInfoService(ClientInfoService clientInfoService) {
        this.clientInfoService = clientInfoService;
    }

    public ScopeCodeService getScopeCodeService() {
        return scopeCodeService;
    }

    public void setScopeCodeService(ScopeCodeService scopeCodeService) {
        this.scopeCodeService = scopeCodeService;
    }

}
