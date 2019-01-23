package com.harmony.kindless.user.service.impl;

import com.harmony.kindless.apis.ResponseCodes;
import com.harmony.kindless.apis.domain.user.User;
import com.harmony.kindless.user.repository.UserRepository;
import com.harmony.kindless.user.service.UserAuthorityService;
import com.harmony.kindless.user.service.UserService;
import com.harmony.umbrella.data.Selections;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import com.harmony.umbrella.jwt.user.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        if ("bean".equalsIgnoreCase(username)) {
            User user = new User();
            user.setUsername(username);
            user.setGender(1);
            user.setNickname("Winnie Enis");
            user.setRegisterAt(new Date());
            return user;
        }
        return queryWith()
                .equal("username", username)
                .getSingleResult()
                .orElseThrow(ResponseCodes.NOT_FOUND::toException);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return queryWith()
                .equal("username", username)
                .execute()
                .getSingleResult(Selections.of("id", "username", "password"))
                .mapToEntity(User.class)
                .map(this::buildUserDetails)
                .orElse(null);
    }

    @Override
    public JwtUserDetails loadUserById(Long userId) {
        return findById(userId)
                .map(this::buildUserDetails)
                .orElse(null);
    }

    private JwtUserDetails buildUserDetails(User user) {
        List<String> userAuthorities = userAuthorityService.getUserAuthorities(user.getId());
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(userAuthorities.toArray(new String[0]))
                .build();
        return new JwtUserDetails(user.getId(), userDetails);
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
