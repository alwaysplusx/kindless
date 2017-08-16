package com.harmony.kindless.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.domain.WebToken;
import com.harmony.kindless.domain.repository.UserRepository;
import com.harmony.kindless.domain.repository.WebTokenRepository;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class UserService extends ServiceSupport<User, Long> {

    @Autowired
    private UserRepository userReopsitory;

    @Autowired
    private WebTokenRepository webTokenRepository;

    @Override
    protected QueryableRepository<User, Long> getRepository() {
        return userReopsitory;
    }

    public User findUserByWebToken(String webToken) {
        WebToken token = webTokenRepository.findOne(webToken);
        return token != null ? findByUsername(token.getUsername()) : null;
    }

    public User findByUsername(String username) {
        return userReopsitory.findByUsername(username);
    }

    public User deleteByUsername(String username) {
        User user = findByUsername(username);
        delete(user);
        return user;
    }

    public void deleteAll(List<String> usernames) {
        for (String username : usernames) {
            deleteByUsername(username);
        }
    }

}
