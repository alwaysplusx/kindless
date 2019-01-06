package com.harmony.kindless.security.config;

import com.harmony.kindless.security.jwt.JwtTokenDecoder;
import com.harmony.kindless.security.jwt.JwtTokenExtractor;
import com.harmony.kindless.security.support.Auth0JwtTokenHandler;
import com.harmony.kindless.security.web.authentication.JwtAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuxii
 */
public class JwtAuthenticationConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractHttpConfigurer<JwtAuthenticationConfigurer<H>, H> {

    private List<JwtTokenExtractor> jwtTokenExtractors = new ArrayList<>();

    private JwtTokenDecoder jwtTokenDecoder = new Auth0JwtTokenHandler();

    public JwtAuthenticationConfigurer<H> jwtTokenDecoder(JwtTokenDecoder jwtTokenDecoder) {
        this.jwtTokenDecoder = jwtTokenDecoder;
        return this;
    }

    public JwtAuthenticationConfigurer<H> addJwtTokenExtractor(JwtTokenExtractor jwtTokenExtractor) {
        this.jwtTokenExtractors.add(jwtTokenExtractor);
        return this;
    }

    public JwtAuthenticationConfigurer jwtTokenExtractors(List<JwtTokenExtractor> extractors) {
        this.jwtTokenExtractors.clear();
        this.jwtTokenExtractors.addAll(extractors);
        return this;
    }

    @Override
    public void configure(H http) throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        filter.setJwtTokenExtractors(jwtTokenExtractors);
        filter.setJwtTokenDecoder(jwtTokenDecoder);
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        http.addFilterBefore(postProcess(filter), UsernamePasswordAuthenticationFilter.class);
    }

}
