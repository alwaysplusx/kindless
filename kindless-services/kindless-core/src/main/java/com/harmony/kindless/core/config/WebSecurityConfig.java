package com.harmony.kindless.core.config;

import com.harmony.kindless.apis.support.AjaxAuthenticationHandler;
import com.harmony.kindless.apis.support.HttpHeaderSecurityTokenExtractor;
import com.harmony.kindless.core.userdetails.IdentityUserDetailsService;
import com.harmony.umbrella.security.configurers.SecurityTokenAuthenticationConfigurer;
import com.harmony.umbrella.security.configurers.SecurityTokenAuthenticationProviderConfigurer;
import com.harmony.umbrella.security.jwt.JwtTokenGenerator;
import com.harmony.umbrella.security.jwt.support.JwtAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author wuxii
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final IdentityUserDetailsService userDetailsService;

	private final JwtTokenGenerator jwtTokenGenerator;

	@Autowired
	public WebSecurityConfig(IdentityUserDetailsService userDetailsService, JwtTokenGenerator jwtTokenGenerator) {
		this.userDetailsService = userDetailsService;
		this.jwtTokenGenerator = jwtTokenGenerator;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// add success handle and unsuccessful handle
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(new BCryptPasswordEncoder());

		auth.apply(new SecurityTokenAuthenticationProviderConfigurer<>())
				.securityTokenUserDetailsService(userDetailsService);
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
				.antMatchers("/test/**").anonymous()
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
            .apply(new SecurityTokenAuthenticationConfigurer<>())
				.addSecurityTokenExtractor(HttpHeaderSecurityTokenExtractor.INSTANCE)
				.and()
            .exceptionHandling()
                .accessDeniedHandler(authenticationHandler)
                .authenticationEntryPoint(authenticationHandler);
        // @formatter:on
	}

}
