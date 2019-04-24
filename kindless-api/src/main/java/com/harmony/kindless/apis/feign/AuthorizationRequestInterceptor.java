package com.harmony.kindless.apis.feign;

import com.harmony.umbrella.security.authentication.SecurityTokenAuthentication;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author wuxii
 */
@Slf4j
public class AuthorizationRequestInterceptor implements RequestInterceptor {

	private final String name;

	public AuthorizationRequestInterceptor() {
		this("Authorization");
	}

	public AuthorizationRequestInterceptor(String name) {
		this.name = name;
	}

	@Override
	public void apply(RequestTemplate template) {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication != null
				&& authentication.isAuthenticated()
				&& authentication instanceof SecurityTokenAuthentication) {
			String qualifierToken = ((SecurityTokenAuthentication) authentication).getSecurityToken().getQualifierToken();
			log.debug("add authorization token to header, {}={}", name, qualifierToken);
			template.header(name, qualifierToken);
		}
	}

}
