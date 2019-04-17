package com.harmony.kindless.apis.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author wuxii
 */
public class AuthorizationRequestInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		template.getRequestVariables();
		template.header("", "");
	}

}
