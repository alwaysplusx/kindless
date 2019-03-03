package com.harmony.kindless.core.service.impl;

import com.harmony.kindless.apis.ResponseCodes;
import com.harmony.kindless.apis.domain.core.User;
import com.harmony.kindless.core.repository.UserRepository;
import com.harmony.kindless.core.service.UserService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @author wuxii
 */
@Primary
@Service
public class UserServiceImpl extends ServiceSupport<User, Long> implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User getByUsername(String username) {
		return queryWith()
				.equal("username", username)
				.getSingleResult()
				.orElseThrow(ResponseCodes.NOT_FOUND::toException);
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
