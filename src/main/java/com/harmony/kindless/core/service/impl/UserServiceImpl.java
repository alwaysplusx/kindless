package com.harmony.kindless.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.repository.UserRepository;
import com.harmony.kindless.core.service.UserService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class UserServiceImpl extends ServiceSupport<User, Long> implements UserService {

    @Autowired
    private UserRepository userReopsitory;

    @Override
    public User findByUsername(String username) {
        return userReopsitory.findByUsername(username);
    }

    @Override
    protected QueryableRepository<User, Long> getRepository() {
        return userReopsitory;
    }

}
