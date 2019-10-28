package com.kindless.user.user.service.impl;

import com.kindless.apis.Responses;
import com.kindless.user.domain.User;
import com.kindless.user.user.repository.UserRepository;
import com.kindless.user.user.service.UserService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
        return queryWith()
                .equal("username", username)
                .getSingleResult()
                .orElseThrow(Responses.NOT_EXISTS::toException);
    }

    @CacheEvict(cacheNames = "user:get-by-name", key = "#p0.username")
    @Override
    public User saveOrUpdate(User entity) {
        return super.saveOrUpdate(entity);
    }

    // TODO delete by id remove cached user by name

    @Override
    protected QueryableRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    protected Class<User> getDomainClass() {
        return userRepository.getDomainClass();
    }

}
