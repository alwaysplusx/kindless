package com.harmony.kindless.security.config;

import com.harmony.kindless.security.IdentityUserDetailsService;
import com.harmony.kindless.security.authentication.JwtAuthenticationProvider;
import com.harmony.kindless.security.jwt.JwtTokenDecoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder;

/**
 * @author wuxii
 */
public class JwtAuthenticationProviderConfigurer<B extends ProviderManagerBuilder<B>>
        extends SecurityConfigurerAdapter<AuthenticationManager, B> {

    private IdentityUserDetailsService identityUserDetailsService;
    private JwtTokenDecoder jwtTokenDecoder;

    public JwtAuthenticationProviderConfigurer<B> identityUserDetailsService(IdentityUserDetailsService identityUserDetailsService) {
        this.identityUserDetailsService = identityUserDetailsService;
        return this;
    }

    public JwtAuthenticationProviderConfigurer<B> jwtTokenDecoder(JwtTokenDecoder jwtTokenDecoder) {
        this.jwtTokenDecoder = jwtTokenDecoder;
        return this;
    }

    @Override
    public void configure(B builder) {
        JwtAuthenticationProvider jwtAuthenticationProvider = postProcess(build());
        builder.authenticationProvider(jwtAuthenticationProvider);
    }

    private JwtAuthenticationProvider build() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(identityUserDetailsService);
        provider.setJwtTokenDecoder(jwtTokenDecoder);
        return provider;
    }

}
