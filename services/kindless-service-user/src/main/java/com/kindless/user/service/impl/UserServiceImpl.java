package com.kindless.user.service.impl;

import com.kindless.core.service.ServiceSupport;
import com.kindless.domain.user.User;
import com.kindless.user.repository.UserRepository;
import com.kindless.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxii
 */
@RequiredArgsConstructor
@Service
@RestController
public class UserServiceImpl extends ServiceSupport<User> implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    protected PagingAndSortingRepository<User, Long> getRepository() {
        return userRepository;
    }

}
