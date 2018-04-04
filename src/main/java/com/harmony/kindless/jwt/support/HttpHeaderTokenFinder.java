package com.harmony.kindless.jwt.support;

import com.harmony.kindless.jwt.JwtToken;
import com.harmony.kindless.jwt.JwtTokenFinder;
import com.harmony.umbrella.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxii@foxmail.com
 */
public class HttpHeaderTokenFinder implements JwtTokenFinder {

    public static final String TOKEN_HEADER_KEY = "X-Authorization";
    public static final String TOKEN_HEADER_PREFIX = "";

    private String headerKey;
    private String valuePrefix;

    public HttpHeaderTokenFinder() {
        this(TOKEN_HEADER_KEY, TOKEN_HEADER_PREFIX);
    }

    public HttpHeaderTokenFinder(String headerKey) {
        this(headerKey, TOKEN_HEADER_PREFIX);
    }

    public HttpHeaderTokenFinder(String headerKey, String valuePrefix) {
        this.headerKey = headerKey;
        this.valuePrefix = valuePrefix;
    }

    @Override
    public JwtToken find(HttpServletRequest request) {
        String val = request.getHeader(headerKey);
        if (StringUtils.isNotBlank(val) && (valuePrefix == null || val.startsWith(valuePrefix))) {
            return parseToken(valuePrefix == null ? val : val.substring(valuePrefix.length()), request);
        }
        return null;
    }

    protected JwtToken parseToken(String token, HttpServletRequest request) {
        return new JwtToken(token, request);
    }

    public String getHeaderKey() {
        return headerKey;
    }

    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    public String getValuePrefix() {
        return valuePrefix;
    }

    public void setValuePrefix(String valuePrefix) {
        this.valuePrefix = valuePrefix;
    }
}
