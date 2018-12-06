package com.harmony.kindless.security.config;

import com.harmony.kindless.security.authentication.JwtAuthenticationProvider;
import com.harmony.kindless.security.support.AjaxAuthenticationFailureHandler;
import com.harmony.kindless.security.support.AjaxAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add success handle and unsuccessful handle
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
        auth.authenticationProvider(new JwtAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        http.anonymous();

        // @formatter:off
//        http
//            .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                // TODO update set user token service
//                .successHandler(new AjaxAuthenticationSuccessHandler(null))
//                .failureHandler(new AjaxAuthenticationFailureHandler())
//                .and()
//            .httpBasic();
        // @formatter:on
    }

}
