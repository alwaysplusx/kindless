package com.harmony.kindless.oauth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.OAuthResponse.OAuthErrorResponseBuilder;
import org.apache.oltu.oauth2.common.message.OAuthResponse.OAuthResponseBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author wuxii@foxmail.com
 */
public class OAuthUtils {

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

    public static OAuthResponseWriter createWriter(HttpServletResponse response) {
        return new OAuthResponseWriter(response);
    }

    public static OAuthResponseWriter createWriter(NativeWebRequest webRequest) {
        return createWriter(webRequest.getNativeResponse(HttpServletResponse.class));
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

    public static class OAuthResponseWriter {

        protected HttpServletResponse response;

        protected OAuthResponseWriter(HttpServletResponse response) {
            this.response = response;
        }

        public void writeResponse(OAuthResponse oauthResponse) throws IOException {
            int status = oauthResponse.getResponseStatus();
            if (status == HttpServletResponse.SC_FOUND //
                    || status == HttpServletResponse.SC_MOVED_PERMANENTLY) {
                writeRedirect(oauthResponse);
            } else {
                writeBody(oauthResponse);
            }
        }

        protected void writeBody(OAuthResponse oauthResponse) throws IOException {
            Map<String, String> headers = oauthResponse.getHeaders();
            for (String key : headers.keySet()) {
                response.addHeader(key, headers.get(key));
            }
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(oauthResponse.getResponseStatus());
            PrintWriter writer = response.getWriter();
            writer.write(oauthResponse.getBody());
            writer.flush();
        }

        protected void writeRedirect(OAuthResponse oauthResponse) throws IOException {
            final Map<String, String> headers = oauthResponse.getHeaders();
            for (String key : headers.keySet()) {
                response.addHeader(key, headers.get(key));
            }
            response.setStatus(oauthResponse.getResponseStatus());
            response.sendRedirect(oauthResponse.getLocationUri());
        }

    }

    public static enum OAuthResponseType {
        Body, Json, Header, Query
    }
}
