package com.harmony.kindless.moment.config;

import com.harmony.umbrella.security.jwt.JwtTokenHandler;
import com.harmony.umbrella.security.jwt.JwtUserDetailsService;
import com.harmony.umbrella.security.jwt.configurers.JwtAuthenticationConfigurer;
import com.harmony.umbrella.security.jwt.configurers.JwtAuthenticationProviderConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author wuxii
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenHandler jwtTokenHandler;
    private final JwtUserDetailsService jwtUserDetailsService;

    public WebSecurityConfig(JwtTokenHandler jwtTokenHandler, JwtUserDetailsService jwtUserDetailsService) {
        this.jwtTokenHandler = jwtTokenHandler;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.apply(new JwtAuthenticationProviderConfigurer<>())
                .jwtUserDetailsService(jwtUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers("/test/**").anonymous()
                .anyRequest()
                .authenticated()
                .and()
            .apply(new JwtAuthenticationConfigurer<>())
                .jwtTokenDecoder(jwtTokenHandler)
                .excludeRequestMatcher()
                    .excludeUrls("/test/**")
            .and();
        // @formatter:on
    }

}
