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
public class OAuthResponseWriter {

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

    public static OAuthResponseWriter createWriter(HttpServletResponse response) {
        return new OAuthResponseWriter(response);
    }

    public static OAuthResponseWriter createWriter(NativeWebRequest webRequest) {
        return createWriter(webRequest.getNativeResponse(HttpServletResponse.class));
    }

}
