package com.harmony.kindless.oauth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.harmony.kindless.oauth.OAuthUtils.OAuthResponseType;

/**
 * 根据grant_type来做request的分发器
 * 
 * @author wuxii@foxmail.com
 */
public class OAuthDispatcher {

    private OAuthRequestHandlerComposite requestHandlers;

    public OAuthDispatcher() {
    }

    public OAuthDispatcher(OAuthRequestHandler... handlers) {
        this(Arrays.asList(handlers));
    }

    public OAuthDispatcher(List<OAuthRequestHandler> handlers) {
        this.requestHandlers = new OAuthRequestHandlerComposite(handlers);
    }

    public OAuthResponse dispatch(OAuthRequest oauthRequest) throws IOException {
        OAuthResponse oauthResponse = null;
        try {
            if (requestHandlers.support(oauthRequest)) {
                oauthResponse = requestHandlers.handle(oauthRequest);
            }
        } catch (OAuthProblemException e) {
            // request can't handle
            oauthResponse = OAuthUtils.parseException(e, OAuthResponseType.Body);
        } catch (OAuthSystemException e) {
            throw new IllegalStateException(e);
        }
        // not support
        if (oauthResponse == null) {
        }
        return oauthResponse;
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

    public static final class OAuthRequestHandlerComposite implements OAuthRequestHandler {

        private final List<OAuthRequestHandler> requestHandlers = new ArrayList<>();

        public OAuthRequestHandlerComposite(List<OAuthRequestHandler> handlers) {
            this.requestHandlers.addAll(handlers);
        }

        @Override
        public boolean support(OAuthRequest request) {
            return getRequestHandler(request) != null;
        }

        @Override
        public OAuthResponse handle(OAuthRequest request) throws OAuthProblemException, OAuthSystemException {
            OAuthRequestHandler requestHandler = getRequestHandler(request);
            if (requestHandler == null) {
                throw OAuthProblemException.error("illegal grant type " + request.getParam(OAuth.OAUTH_GRANT_TYPE));
            }
            return requestHandler.handle(request);
        }

        private OAuthRequestHandler getRequestHandler(OAuthRequest request) {
            for (OAuthRequestHandler requestHandler : requestHandlers) {
                if (requestHandler.support(request)) {
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
