package com.harmony.kindless.security.web.authentication;

import com.harmony.kindless.security.jwt.JwtDecodeException;
import com.harmony.kindless.security.jwt.JwtToken;
import com.harmony.kindless.security.jwt.JwtTokenDecoder;
import com.harmony.kindless.security.jwt.JwtTokenExtractor;
import org.springframework.core.OrderComparator;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuxii
 */
public class JwtAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    public static final String JWT_DECODE_EXCEPTION = JwtDecodeException.class.getName();

    private List<JwtTokenExtractor> jwtTokenExtractors = new ArrayList<>();

    private JwtTokenDecoder jwtTokenDecoder;

    protected String extractJwtToken(HttpServletRequest request) {
        for (JwtTokenExtractor extractor : jwtTokenExtractors) {
            String tokenValue = extractor.extract(request);
            if (StringUtils.hasText(tokenValue)) {
                return tokenValue;
            }
        }
        return null;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        JwtToken jwtToken = null;
        try {
            String tokenValue = extractJwtToken(request);
            if (StringUtils.hasText(tokenValue)) {
                jwtToken = jwtTokenDecoder.decode(tokenValue);
            }
        } catch (Exception e) {
            request.setAttribute(JWT_DECODE_EXCEPTION, e);
        }
        return jwtToken;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        OrderComparator.sort(jwtTokenExtractors);
    }

    public JwtAuthenticationFilter setJwtTokenDecoder(JwtTokenDecoder jwtTokenDecoder) {
        this.jwtTokenDecoder = jwtTokenDecoder;
        return this;
    }

    public void setJwtTokenExtractors(List<JwtTokenExtractor> jwtTokenExtractors) {
        this.jwtTokenExtractors = jwtTokenExtractors;
    }

}
