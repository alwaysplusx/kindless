package com.harmony.kindless.oauth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.OAuthResponse.OAuthErrorResponseBuilder;
import org.apache.oltu.oauth2.common.message.OAuthResponse.OAuthResponseBuilder;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * 根据grant_type来做request的分发器
 * 
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

    public void dispatch(NativeWebRequest webRequest) throws IOException {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        OAuthRequest oauthRequest = null;
        OAuthResponse oauthResponse = null;
        try {
            oauthRequest = new OAuthTokenRequest(request);
            if (requestHandlers.support(oauthRequest)) {
                oauthResponse = requestHandlers.handle(oauthRequest);
            }
        } catch (OAuthProblemException e) {
            // request can't handle
            oauthResponse = parseException(e, OAuthResponseType.Body);
        } catch (OAuthSystemException e) {
            throw new IllegalStateException(e);
        }
        // not support
        if (oauthResponse == null) {
        }
        OAuthResponseWriter//
                .createWriter(response)//
                .writeResponse(oauthResponse);
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

    private OAuthResponse parseException(OAuthProblemException ex, OAuthResponseType type) {
        return parseException(ex, new OAuthResponseBuilderContainer() {

            @Override
            public OAuthResponse build(OAuthResponseBuilder builder) throws OAuthSystemException {
                switch (type) {
                case Body:
                    return builder.buildBodyMessage();
                case Header:
                    return builder.buildHeaderMessage();
                case Json:
                    return builder.buildJSONMessage();
                case Query:
                    return builder.buildQueryMessage();
                }
                throw new IllegalArgumentException("unknow response type " + type);
            }
        });
    }

    private OAuthResponse parseException(OAuthProblemException ex, OAuthResponseBuilderContainer builderContainer) {
        OAuthErrorResponseBuilder builder = OAuthResponse//
                .errorResponse(ex.getResponseStatus())//
                .setError(ex.getError())//
                .setErrorDescription(ex.getDescription())//
                .setErrorUri(ex.getUri())//
                .location(ex.getRedirectUri());//
        Map<String, String> params = ex.getParameters();
        if (params != null && !params.isEmpty()) {
            for (Entry<String, String> entry : params.entrySet()) {
                builder.setParam(entry.getKey(), entry.getValue());
            }
        }
        try {
            return builderContainer.build(builder);
        } catch (OAuthSystemException e) {
            throw new IllegalStateException(e);
        }
    }

    public interface OAuthResponseBuilderContainer {

        OAuthResponse build(OAuthResponseBuilder builder) throws OAuthSystemException;

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

    private static enum OAuthResponseType {
        Body, Json, Header, Query
    }

}
