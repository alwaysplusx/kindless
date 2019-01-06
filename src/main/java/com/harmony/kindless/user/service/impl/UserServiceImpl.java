package com.harmony.kindless.user.service.impl;

import com.harmony.kindless.apis.domain.user.User;
import com.harmony.kindless.security.IdentityUserDetails;
import com.harmony.kindless.user.repository.UserRepository;
import com.harmony.kindless.user.service.UserAuthorityService;
import com.harmony.kindless.user.service.UserService;
import com.harmony.umbrella.data.Selections;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return queryWith()
                .equal("username", username)
                .execute()
                .getSingleResult(Selections.of("id", "username", "password"))
                .mapTo(User.class)
                .map(this::buildUserDetails)
                .orElse(null);
    }

    @Override
    public IdentityUserDetails loadUserById(Long userId) {
        return findById(userId)
                .map(this::buildUserDetails)
                .orElse(null);
    }

    private IdentityUserDetails buildUserDetails(User user) {
        List<String> userAuthorities = userAuthorityService.getUserAuthorities(user.getId());
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(userAuthorities.toArray(new String[0]))
                .build();
        return new IdentityUserDetails(user.getId(), userDetails);
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
