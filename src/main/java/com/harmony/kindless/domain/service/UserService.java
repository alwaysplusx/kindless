package com.harmony.kindless.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.repository.UserRepository;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class UserService extends ServiceSupport<User, String> {

    @Autowired
    private UserRepository userReopsitory;

    @Override
    protected QueryableRepository getRepository() {
        return userReopsitory;
    }

}
