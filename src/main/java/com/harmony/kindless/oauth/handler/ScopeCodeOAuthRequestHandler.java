package com.harmony.kindless.oauth.handler;

import java.util.Set;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.OAuthResponse.OAuthResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.harmony.kindless.core.domain.ClientInfo;
import com.harmony.kindless.core.service.UserService;
import com.harmony.kindless.oauth.OAuthRequestHandler;
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.kindless.oauth.service.ClientInfoService;
import com.harmony.kindless.oauth.service.ScopeCodeService;

/**
 * 用户经过第三方客户端引导到达authorization_code handler. 此handler将用户所允许授予第三方的权限提交给授权服务器来生成一阶段授权凭证(scope code). scope
 * code生成后授权服务器将带重定向到第三方的回调url并带上scope code. 第三方接收到后将可以进行二阶段的access_token的获取
 * 
 * @author wuxii@foxmail.com
 * @see AuthorizationCodeOAuthRequestHandler
 */
public class ScopeCodeOAuthRequestHandler implements OAuthRequestHandler {

    @Autowired
    private ClientInfoService clientInfoService;
    @Autowired
    private ScopeCodeService scopeCodeService;
    @Autowired
    private UserService userService;

    public ScopeCodeOAuthRequestHandler() {
    }

    public ScopeCodeOAuthRequestHandler(ClientInfoService clientInfoService, ScopeCodeService scopeCodeService) {
        this.clientInfoService = clientInfoService;
        this.scopeCodeService = scopeCodeService;
    }

    public boolean support(OAuthRequest request) {
        // 通过responseType确定是否进行handler support
        String responseType = request.getParam(OAuth.OAUTH_RESPONSE_TYPE);
        return OAuth.OAUTH_CODE.equals(responseType);
    }

    public OAuthResponse handle(OAuthRequest request) throws OAuthProblemException, OAuthSystemException {
        String clientId = request.getClientId();
        String state = request.getParam("state");
        Set<String> scopes = request.getScopes();
        // UserPrincipal up = null;
        // if (up == null || up.getIdentity() == null) {
        // throw OAuthProblemException//
        // .error("unauthorized_request")//
        // .responseStatus(401);
        // }
        // User user = userService.findById((Long) up.getIdentity());
        // if (user == null) {
        // throw OAuthProblemException//
        // .error("unknow user " + up.getIdentity())//
        // .responseStatus(401);
        // }

        ClientInfo clientInfo = clientInfoService.findById(clientId);
        if (clientInfo == null) {
            throw OAuthProblemException//
                    .error("invalid client_id " + clientId)//
                    .responseStatus(403);
        }
        String redirectURI = request.getRedirectURI();
        if (!clientInfo.getRedirectUri().equals(redirectURI)) {
            throw OAuthProblemException//
                    .error("invalid reddirect_uri " + redirectURI)//
                    .responseStatus(403);
        }

        ScopeCode code = scopeCodeService.grant(null, clientInfo, scopes);

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

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
