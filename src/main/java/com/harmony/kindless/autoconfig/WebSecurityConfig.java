package com.harmony.kindless.autoconfig;

import com.harmony.kindless.security.IdentityUserDetailsService;
import com.harmony.kindless.security.config.JwtAuthenticationConfigurer;
import com.harmony.kindless.security.config.JwtAuthenticationProviderConfigurer;
import com.harmony.kindless.security.jwt.support.HttpHeaderJwtTokenExtractor;
import com.harmony.kindless.security.support.AjaxAuthenticationHandler;
import com.harmony.kindless.security.support.Auth0JwtTokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author wuxii
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${}")
    private String jwtSignature;

    private final UserDetailsService userDetailsService;

    private final IdentityUserDetailsService identityUserDetailsService;

    private Auth0JwtTokenHandler auth0JwtTokenHandler = new Auth0JwtTokenHandler();

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, IdentityUserDetailsService identityUserDetailsService) {
        this.userDetailsService = userDetailsService;
        this.identityUserDetailsService = identityUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add success handle and unsuccessful handle
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
        // add jwt support
        auth.apply(new JwtAuthenticationProviderConfigurer())
                .identityUserDetailsService(identityUserDetailsService)
                .jwtTokenDecoder(auth0JwtTokenHandler);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AjaxAuthenticationHandler authenticationHandler = new AjaxAuthenticationHandler(auth0JwtTokenHandler);
        // @formatter:off
        http
            .csrf().disable()
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .successHandler(authenticationHandler)
                // .failureHandler(new AjaxAuthenticationFailureHandler())
                .and()
            .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(authenticationHandler)
                .and()
            .apply(new JwtAuthenticationConfigurer<>())
                .addJwtTokenExtractor(HttpHeaderJwtTokenExtractor.INSTANCE)
                .jwtTokenDecoder(auth0JwtTokenHandler)
                .and()
            .exceptionHandling()
                .accessDeniedHandler(authenticationHandler)
                .authenticationEntryPoint(authenticationHandler);
        // @formatter:on
    }

}
