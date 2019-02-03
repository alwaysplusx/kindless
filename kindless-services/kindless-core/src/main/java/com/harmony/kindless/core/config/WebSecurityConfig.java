package com.harmony.kindless.core.config;

import com.harmony.kindless.apis.support.AjaxAuthenticationHandler;
import com.harmony.umbrella.security.jwt.JwtTokenGenerator;
import com.harmony.umbrella.security.jwt.support.JwtAuthenticationSuccessHandler;
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

	private final JwtTokenGenerator jwtTokenGenerator;

	@Autowired
	public WebSecurityConfig(UserDetailsService userDetailsService, JwtTokenGenerator jwtTokenGenerator) {
		this.userDetailsService = userDetailsService;
		this.jwtTokenGenerator = jwtTokenGenerator;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// add success handle and unsuccessful handle
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		AjaxAuthenticationHandler authenticationHandler = new AjaxAuthenticationHandler();
		// @formatter:off
        http
			.sessionManagement().disable()
            .securityContext().disable()
            .authorizeRequests()
				.antMatchers("/security/**").anonymous()
                .anyRequest().authenticated()
                .and()
            .csrf().disable()
            .headers().frameOptions().disable().and()
            .formLogin()
                .successHandler(new JwtAuthenticationSuccessHandler(jwtTokenGenerator))
                .and()
            .logout()
                .addLogoutHandler(authenticationHandler)
                .and()
//            .apply(new JwtAuthenticationConfigurer<>())
//                .addJwtTokenExtractor(HttpHeaderJwtTokenExtractor.INSTANCE)
//                .jwtTokenDecoder(jwtTokenHandler)
//                .excludeRequestMatcher()
//                    .excludeUrls("/error", "/h2/**")
//                .and()
            .exceptionHandling()
                .accessDeniedHandler(authenticationHandler)
                .authenticationEntryPoint(authenticationHandler);
        // @formatter:on
	}

}
