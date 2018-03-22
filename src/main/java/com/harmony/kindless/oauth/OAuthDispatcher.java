package com.harmony.kindless.oauth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import com.harmony.kindless.oauth.OAuthUtils.OAuthResponseType;

/**
 * 根据grant_type来做request的分发
 * 
 * @author wuxii@foxmail.com
 */
public class OAuthDispatcher {

    /**
     * 所支持的oauth合集
     */
    private OAuthRequestHandlerComposite requestHandlers;

    public OAuthDispatcher() {
        // this.requestHandlers = new OAuthRequestHandlerComposite();
    }

    public OAuthDispatcher(OAuthRequestHandler... handlers) {
        this(Arrays.asList(handlers));
    }

    public OAuthDispatcher(List<OAuthRequestHandler> handlers) {
        this.setOAuthRequestHandlers(handlers);
    }

    /**
     * 对oauth request进行分发
     * 
     * @param oauthRequest
     *            oauth请求
     * @return oauth响应
     * @throws IOException
     */
    public OAuthResponse dispatch(OAuthRequest oauthRequest) throws IOException {
        final String grantType = oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE);
        OAuthResponse oauthResponse = null;
        try {
            if (requestHandlers.support(oauthRequest)) {
                oauthResponse = requestHandlers.handle(oauthRequest);
            }
        } catch (OAuthProblemException e) {
            // request can't handle
            oauthResponse = OAuthUtils.parseException(e, OAuthResponseType.Json);
        } catch (OAuthSystemException e) {
            try {
                oauthResponse = OAuthASResponse//
                        .errorResponse(500)//
                        .setError(e.getMessage())//
                        .buildJSONMessage();
            } catch (OAuthSystemException e1) {
                throw new IllegalStateException(e);
            }
        }
        // not support
        if (oauthResponse == null) {
            try {
                oauthResponse = OAuthASResponse//
                        .errorResponse(403)//
                        .setError("unsupported grant_type " + grantType)//
                        .buildJSONMessage();
            } catch (OAuthSystemException e) {
                // ignore
                throw new IllegalStateException(e);
            }
        }
        return oauthResponse;
    }

    /**
     * 设置所支持的oauth handler
     * 
     * @param handlers
     */
    public void setOAuthRequestHandlers(List<OAuthRequestHandler> handlers) {
        this.requestHandlers = new OAuthRequestHandlerComposite(handlers);
    }

    public OAuthRequestHandlerComposite getOAuthRequestHandlerComposite() {
        return requestHandlers;
    }

    public void setOAuthRequestHandlerComposite(OAuthRequestHandlerComposite requestHandlers) {
        this.requestHandlers = requestHandlers;
    }

    public static final class OAuthRequestHandlerComposite implements OAuthRequestHandler {

        private final List<OAuthRequestHandler> requestHandlers = new ArrayList<>();

        public OAuthRequestHandlerComposite() {
        }

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
