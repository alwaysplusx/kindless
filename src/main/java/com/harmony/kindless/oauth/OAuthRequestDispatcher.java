package com.harmony.kindless.oauth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author wuxii@foxmail.com
 */
public class OAuthRequestDispatcher {

    private OAuthRequestHandlerComposite requestHandlers;

    public void dispatch(NativeWebRequest webRequest) throws OAuthSystemException, IOException {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        GrantType grantType = getGrantType(webRequest.getParameter(OAuth.OAUTH_GRANT_TYPE));
        try {
            OAuthRequest oauthRequest = GrantType.AUTHORIZATION_CODE.equals(grantType) ? new OAuthAuthzRequest(request) : new OAuthTokenRequest(request);
            if (requestHandlers.support(grantType)) {
                requestHandlers.handle(oauthRequest, webRequest);
            } else {
                /*OAuthResponse oauthResponse = null;
                oauthResponse = OAuthResponse//
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)//
                        .setError(OAuthError.CodeResponse.INVALID_REQUEST)//
                        .setErrorDescription("unsupported grant type " + grantType.toString())//
                        .buildJSONMessage();*/
            }
        } catch (OAuthProblemException e) {
        }
    }

    public static final GrantType getGrantType(String grantType) {
        if (grantType != null) {
            for (GrantType g : GrantType.values()) {
                if (g.toString().equals(grantType)) {
                    return g;
                }
            }
        }
        return null;
    }

    private static final GrantType getGrantType(OAuthRequest request) {
        return getGrantType(request.getParam(OAuth.OAUTH_GRANT_TYPE));
    }

    public static final class OAuthRequestHandlerComposite implements OAuthRequestHandler {

        private List<OAuthRequestHandler> requestHandlers = new ArrayList<>();

        @Override
        public boolean support(GrantType grantType) {
            return getRequestHandler(grantType) != null;
        }

        @Override
        public void handle(OAuthRequest request, NativeWebRequest webRequest) throws OAuthSystemException, IOException {
            OAuthRequestHandler requestHandler = getRequestHandler(getGrantType(request));
            if (requestHandler == null) {
                throw new IllegalArgumentException("unknow oauth request " + request.getParam(OAuth.OAUTH_GRANT_TYPE));
            }
            requestHandler.handle(request, webRequest);
        }

        private OAuthRequestHandler getRequestHandler(GrantType grantType) {
            for (OAuthRequestHandler requestHandler : requestHandlers) {
                if (requestHandler.support(grantType)) {
                    return requestHandler;
                }
            }
            return null;
        }

        public OAuthRequestHandlerComposite addHandler(OAuthRequestHandler requestHandler) {
            this.requestHandlers.add(requestHandler);
            return this;
        }

        public OAuthRequestHandlerComposite addHandler(List<OAuthRequestHandler> requestHandlers) {
            if (requestHandlers != null) {
                for (OAuthRequestHandler requestHandler : requestHandlers) {
                    this.requestHandlers.add(requestHandler);
                }
            }
            return this;
        }

    }
}
