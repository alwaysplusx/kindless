package com.kindless.user.service.impl;

import com.kindless.core.service.ServiceSupport;
import com.kindless.user.domain.User;
import com.kindless.user.repository.UserRepository;
import com.kindless.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(cacheNames = "user:get-by-name", key = "#p0")
    public User getByUsername(String username) {
        return null;
    }

}
