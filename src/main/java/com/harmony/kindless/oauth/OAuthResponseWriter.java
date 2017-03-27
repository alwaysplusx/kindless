package com.harmony.kindless.oauth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author wuxii@foxmail.com
 */
public abstract class OAuthResponseWriter {

    protected HttpServletResponse response;

    protected OAuthResponseWriter(HttpServletResponse response) {
        this.response = response;
    }

    public abstract void writeResponse(OAuthResponse oauthResponse) throws IOException;

    public static OAuthResponseWriter bodyWriter(NativeWebRequest webRequest) {
        return bodyWriter(webRequest.getNativeResponse(HttpServletResponse.class));
    }

    public static OAuthResponseWriter redirectWriter(NativeWebRequest webRequest) {
        return redirectWriter(webRequest.getNativeResponse(HttpServletResponse.class));
    }

    public static OAuthResponseWriter bodyWriter(HttpServletResponse response) {
        return new RedirectOAuthResponseWriter(response);
    }

    public static OAuthResponseWriter redirectWriter(HttpServletResponse response) {
        return new BodyOAuthResponseWriter(response);
    }

    private static final class BodyOAuthResponseWriter extends OAuthResponseWriter {

        protected BodyOAuthResponseWriter(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void writeResponse(OAuthResponse oauthResponse) throws IOException {
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

    }

    private static final class RedirectOAuthResponseWriter extends OAuthResponseWriter {

        protected RedirectOAuthResponseWriter(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void writeResponse(OAuthResponse oauthResponse) throws IOException {
            final Map<String, String> headers = oauthResponse.getHeaders();
            for (String key : headers.keySet()) {
                response.addHeader(key, headers.get(key));
            }
            response.setStatus(oauthResponse.getResponseStatus());
            response.sendRedirect(oauthResponse.getLocationUri());
        }

    }
}
