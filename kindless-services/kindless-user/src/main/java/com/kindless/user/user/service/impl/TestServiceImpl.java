package com.kindless.user.user.service.impl;

import com.kindless.user.domain.User;
import com.kindless.user.user.repository.UserRepository;
import com.kindless.user.user.service.TestService;
import com.harmony.umbrella.data.JpaQueryBuilder;
import com.harmony.umbrella.data.QueryBundle;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxii
 */
@Service
public class TestServiceImpl extends ServiceSupport<User, Long> implements TestService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Object get1(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Object get2(String username) {
        // around transaction
        QueryBundle<User> bundle = JpaQueryBuilder
                .newBuilder(User.class)
                .equal("username", username)
                .bundle();
        return userRepository.getSingleResult(bundle);
    }

    @Override
    public Object get3(String username) {
        // no set transaction
        return userRepository.findById(1l);
    }

    @Override
    public Object get4(String username) {
        // pre transaction
        return queryWith()
                .equal("username", username)
                .getSingleResult();
    }

    @Override
    protected QueryableRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    protected Class<User> getDomainClass() {
        return User.class;
    }

}
