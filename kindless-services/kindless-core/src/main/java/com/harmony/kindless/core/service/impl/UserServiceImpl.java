package com.harmony.kindless.core.service.impl;

import com.harmony.kindless.apis.CodeException;
import com.harmony.kindless.apis.ResponseCodes;
import com.harmony.kindless.apis.domain.core.User;
import com.harmony.kindless.apis.support.RestUserDetails;
import com.harmony.kindless.core.repository.UserRepository;
import com.harmony.kindless.core.service.UserAuthorityService;
import com.harmony.kindless.core.service.UserService;
import com.harmony.umbrella.data.Selections;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * @author wuxii
 */
@Primary
@Service
public class UserServiceImpl extends ServiceSupport<User, Long> implements UserService {

	private final UserRepository userRepository;

	private final UserAuthorityService userAuthorityService;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, UserAuthorityService userAuthorityService) {
		this.userRepository = userRepository;
		this.userAuthorityService = userAuthorityService;
	}

	@Override
	public User getByUsername(String username) {
		return queryWith()
				.equal("username", username)
				.getSingleResult()
				.orElseThrow(ResponseCodes.NOT_FOUND::toException);
	}

	@Override
	public RestUserDetails loadUserByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			throw new CodeException(ResponseCodes.ILLEGAL_ARGUMENT);
		}
		return queryWith()
				.equal("username", username)
				.execute()
				.getSingleResult(Selections.of("id", "username", "password", "passwordExpiredAt", "accountStatus", "accountExpiredAt"))
				.mapToEntity(User.class)
				.map(this::buildRestUserDetails)
				.orElseThrow(ResponseCodes.NOT_FOUND::toException);
	}

	private RestUserDetails buildRestUserDetails(User user) {
		long now = System.currentTimeMillis();
		List<String> userAuthorities = userAuthorityService.getUserAuthorities(user.getId());
		return RestUserDetails
				.builder()
				.userId(user.getId())
				.username(user.getUsername())
				.password(user.getPassword())
				.accountNonExpired(now < user.getAccountExpiredAt().getTime())
				.credentialsNonExpired(now < user.getPasswordExpiredAt().getTime())
				.accountNonLocked(!user.isLocked())
				.enabled(user.isEnabled())
				.plainTextAuthorities(new HashSet<>(userAuthorities))
				.build();
	}

	@Override
	protected QueryableRepository<User, Long> getRepository() {
		return userRepository;
	}

	@Override
	protected Class<User> getDomainClass() {
		return userRepository.getDomainClass();
	}


}
