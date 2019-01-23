package com.harmony.kindless.autoconfig;

import com.harmony.kindless.security.web.AjaxAuthenticationHandler;
import com.harmony.umbrella.jwt.JwtTokenHandler;
import com.harmony.umbrella.jwt.configurers.JwtAuthenticationConfigurer;
import com.harmony.umbrella.jwt.configurers.JwtAuthenticationProviderConfigurer;
import com.harmony.umbrella.jwt.support.HttpHeaderJwtTokenExtractor;
import com.harmony.umbrella.jwt.support.JwtAuthenticationSuccessHandler;
import com.harmony.umbrella.jwt.user.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final UserDetailsService userDetailsService;

    private final JwtUserDetailsService jwtUserDetailsService;

    private final JwtTokenHandler jwtTokenHandler;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, JwtUserDetailsService jwtUserDetailsService,
                             JwtTokenHandler jwtTokenHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenHandler = jwtTokenHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add success handle and unsuccessful handle
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
        auth.apply(new JwtAuthenticationProviderConfigurer())
                .jwtUserDetailsService(jwtUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AjaxAuthenticationHandler authenticationHandler = new AjaxAuthenticationHandler();
        // @formatter:off
        http
            .csrf().disable()
            .headers().frameOptions().disable().and()
            // .sessionManagement().disable()
            // .securityContext().disable()
            .authorizeRequests()
                .antMatchers("/test/**").anonymous()
                .antMatchers("/h2/**").anonymous()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .successHandler(new JwtAuthenticationSuccessHandler(jwtTokenHandler))
                .and()
            .logout()
                .addLogoutHandler(authenticationHandler)
                .and()
            .apply(new JwtAuthenticationConfigurer<>())
                .addJwtTokenExtractor(HttpHeaderJwtTokenExtractor.INSTANCE)
                .jwtTokenDecoder(jwtTokenHandler)
                .excludeRequestMatcher()
                    .excludeUrls("/error", "/h2/**")
                .and()
            .exceptionHandling()
                .accessDeniedHandler(authenticationHandler)
                .authenticationEntryPoint(authenticationHandler);
        // @formatter:on
    }

}
