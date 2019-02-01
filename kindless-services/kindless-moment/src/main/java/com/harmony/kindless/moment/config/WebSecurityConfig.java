package com.harmony.kindless.moment.config;

import com.harmony.kindless.apis.support.AjaxAuthenticationHandler;
import com.harmony.kindless.apis.support.HttpHeaderSecurityTokenExtractor;
import com.harmony.umbrella.security.configurers.SecurityTokenAuthenticationConfigurer;
import com.harmony.umbrella.security.configurers.SecurityTokenAuthenticationProviderConfigurer;
import com.harmony.umbrella.security.userdetails.SecurityTokenUserDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author wuxii
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private SecurityTokenUserDetailsService securityTokenUserDetailsService;

	public WebSecurityConfig(SecurityTokenUserDetailsService securityTokenUserDetailsService) {
		this.securityTokenUserDetailsService = securityTokenUserDetailsService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.apply(new SecurityTokenAuthenticationProviderConfigurer<>())
				.securityTokenUserDetailsService(securityTokenUserDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		AjaxAuthenticationHandler authenticationHandler = new AjaxAuthenticationHandler();
		// @formatter:off
        http
            .authorizeRequests()
                .antMatchers("/test/**").anonymous()
                .anyRequest()
                .authenticated()
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
