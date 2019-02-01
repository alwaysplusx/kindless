package com.harmony.kindless.apis.support;

import com.harmony.umbrella.security.SecurityToken;
import com.harmony.umbrella.security.SecurityTokenException;
import com.harmony.umbrella.security.SecurityTokenExtractor;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxii
 */
public class HttpHeaderSecurityTokenExtractor implements SecurityTokenExtractor {

	public static final HttpHeaderSecurityTokenExtractor INSTANCE = new HttpHeaderSecurityTokenExtractor();

	@Override
	public SecurityToken extract(HttpServletRequest request) {
		String securityText = request.getHeader("Authorization");
		if (!StringUtils.hasText(securityText)) {
			return null;
		}
		String[] securityParts = securityText.split(" ");
		if (securityParts.length != 2) {
			throw new SecurityTokenException("bad security token");
		}
		return new SecurityToken(securityParts[0].trim(), securityParts[1].trim());
	}

}
