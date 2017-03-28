package com.harmony.kindless.oauth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author wuxii@foxmail.com
 */
public class OAuthRequestDispatcher {

    private OAuthRequestHandlerComposite requestHandlers;

    public OAuthRequestDispatcher() {
    }

    public OAuthRequestDispatcher(OAuthRequestHandler... handlers) {
        this(Arrays.asList(handlers));
    }

    public OAuthRequestDispatcher(List<OAuthRequestHandler> handlers) {
        this.requestHandlers = new OAuthRequestHandlerComposite(handlers);
    }

    public void dispatch(NativeWebRequest webRequest) throws OAuthSystemException, IOException {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        GrantType grantType = getGrantType(request);
        OAuthRequest oauthRequest = null;
        OAuthResponse oauthResponse = null;
        try {
            oauthRequest = new OAuthTokenRequest(request);
            if (requestHandlers.support(grantType)) {
                oauthResponse = requestHandlers.handle(oauthRequest);
            } else {
                oauthResponse = OAuthResponse//
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)//
                        .setError(OAuthError.CodeResponse.INVALID_REQUEST)//
                        .setErrorDescription("unsupported grant type " + grantType.toString())//
                        .buildJSONMessage();
            }
        } catch (OAuthProblemException e) {
            oauthResponse = OAuthASResponse//
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)//
                    .error(e)//
                    .buildJSONMessage();
        }
        if (oauthResponse == null) {
            oauthResponse = OAuthResponse//
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)//
                    .setError(OAuthError.CodeResponse.INVALID_REQUEST)//
                    .setErrorDescription("can't handler " + grantType + " oauth request!")//
                    .buildJSONMessage();
        }
        OAuthResponseWriter//
                .createWriter(response)//
                .writeResponse(oauthResponse);
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

    public OAuthRequestHandlerComposite getOAuthRequestHandlerComposite() {
        return requestHandlers;
    }

    public void setOAuthRequestHandlerComposite(OAuthRequestHandlerComposite requestHandlers) {
        this.requestHandlers = requestHandlers;
    }

    public static final GrantType getGrantType(HttpServletRequest request) {
        return getGrantType(request.getParameter(OAuth.OAUTH_GRANT_TYPE));
    }

    public static final GrantType getGrantType(OAuthRequest request) {
        return getGrantType(request.getParam(OAuth.OAUTH_GRANT_TYPE));
    }

    public static final class OAuthRequestHandlerComposite implements OAuthRequestHandler {

        private final List<OAuthRequestHandler> requestHandlers = new ArrayList<>();

        public OAuthRequestHandlerComposite(List<OAuthRequestHandler> handlers) {
            this.requestHandlers.addAll(handlers);
        }

        @Override
        public boolean support(GrantType grantType) {
            return getRequestHandler(grantType) != null;
        }

        @Override
        public OAuthResponse handle(OAuthRequest request) throws OAuthSystemException {
            OAuthRequestHandler requestHandler = getRequestHandler(getGrantType(request));
            if (requestHandler == null) {
                throw new IllegalArgumentException("unknow oauth request " + request.getParam(OAuth.OAUTH_GRANT_TYPE));
            }
            return requestHandler.handle(request);
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
