package com.kindless.user.service.impl;

import com.kindless.core.WebRequestException;
import com.kindless.core.service.ServiceSupport;
import com.kindless.domain.user.User;
import com.kindless.user.repository.UserRepository;
import com.kindless.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Path;

/**
 * @author wuxii
 */
@Slf4j
@Service
@RequiredArgsConstructor
@RestController
public class UserServiceImpl extends ServiceSupport<User> implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User register(User user) {
        log.info("do register user: {}", user);
        long existsUsernameCount = userRepository.count((root, query, cb) -> {
            Path<String> username = root.get("username");
            return cb.equal(username, user.getUsername());
        });
        if (existsUsernameCount > 0) {
            throw new WebRequestException("username exists");
        }
        log.info("user register success");
        return save(user);
    }

    @Override
    protected PagingAndSortingRepository<User, Long> getRepository() {
        return userRepository;
    }

}
