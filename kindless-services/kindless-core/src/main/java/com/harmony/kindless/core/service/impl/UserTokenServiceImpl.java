package com.harmony.kindless.core.service.impl;

import com.harmony.kindless.apis.ResponseCodes;
import com.harmony.kindless.apis.domain.core.UserToken;
import com.harmony.kindless.apis.support.RestUserDetails;
import com.harmony.kindless.core.repository.UserTokenRepository;
import com.harmony.kindless.core.service.UserService;
import com.harmony.kindless.core.service.UserTokenService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import com.harmony.umbrella.security.jwt.JwtToken;
import com.harmony.umbrella.security.jwt.JwtTokenDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxii
 */
@Service
public class UserTokenServiceImpl extends ServiceSupport<UserToken, Long> implements UserTokenService {

	@Autowired
	private UserTokenRepository userTokenRepository;

	@Autowired
	private JwtTokenDecoder jwtTokenDecoder;

	@Autowired
	private UserService userService;

	@Override
	public RestUserDetails loadUserByToken(String token) {
		RestUserDetails userDetails = getUserDetailsFromCache(token);
		if (userDetails == null) {
			JwtToken jwtToken = jwtTokenDecoder.decode(token);
			if (jwtToken.isExpired()) {
				throw ResponseCodes.AUTHORIZATION_EXPIRED.toException();
			}
			userDetails = userService.loadUserByUsername(jwtToken.getName());
		}
		if (userDetails == null) {
			throw ResponseCodes.ILLEGAL_ARGUMENT.toException();
		}
		return userDetails;
	}

	protected RestUserDetails getUserDetailsFromCache(String token) {
		// TODO load user details from cache
		return null;
	}

	@Override
	protected QueryableRepository<UserToken, Long> getRepository() {
		return userTokenRepository;
	}

	@Override
	protected Class<UserToken> getDomainClass() {
		return UserToken.class;
	}

}
