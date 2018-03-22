package com.harmony.kindless.oauth;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.OAuthResponse.OAuthErrorResponseBuilder;
import org.apache.oltu.oauth2.common.message.OAuthResponse.OAuthResponseBuilder;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.View;

/**
 * @author wuxii@foxmail.com
 */
public class OAuthUtils {

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

    public static OAuthRequest codeRequest(NativeWebRequest webRequest) throws OAuthProblemException {
        return codeRequest(webRequest.getNativeRequest(HttpServletRequest.class));
    }

    public static OAuthRequest codeRequest(HttpServletRequest request) throws OAuthProblemException {
        try {
            return new OAuthAuthzRequest(request);
        } catch (OAuthSystemException e) {
            throw OAuthProblemException.error(e.getMessage());
        }
    }

    public static OAuthRequest tokenRequest(NativeWebRequest webRequest) throws OAuthProblemException {
        return tokenRequest(webRequest.getNativeRequest(HttpServletRequest.class));
    }

    public static OAuthRequest tokenRequest(HttpServletRequest request) throws OAuthProblemException {
        try {
            return new OAuthTokenRequest(request);
        } catch (OAuthSystemException e) {
            throw OAuthProblemException.error(e.getMessage());
        }
    }

    public static View toResponseView(OAuthResponse oauthResp) {
        return new OAuthResponseView(oauthResp);
    }

    public static OAuthResponse parseException(OAuthProblemException ex, OAuthResponseType type) {
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

    public static OAuthResponse parseException(OAuthProblemException ex, OAuthResponseBuilderContainer builderContainer) {
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

    private static class OAuthResponseView implements View {

        private OAuthResponse oauthResponse;

        public OAuthResponseView(OAuthResponse oauthResponse) {
            this.oauthResponse = oauthResponse;
        }

        @Override
        public String getContentType() {
            return MediaType.APPLICATION_JSON_UTF8_VALUE;
        }

        @Override
        public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
            int status = oauthResponse.getResponseStatus();
            final Map<String, String> headers = oauthResponse.getHeaders();
            for (String key : headers.keySet()) {
                response.addHeader(key, headers.get(key));
            }
            response.setStatus(oauthResponse.getResponseStatus());
            if (status == HttpServletResponse.SC_FOUND //
                    || status == HttpServletResponse.SC_MOVED_PERMANENTLY) {
                response.sendRedirect(oauthResponse.getLocationUri());
            } else {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(oauthResponse.getBody());
                writer.flush();
            }
        }

    }

    public static enum OAuthResponseType {
        Body, Json, Header, Query
    }
}
