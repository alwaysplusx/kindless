package com.harmony.kindless.user.service.impl;

import com.harmony.kindless.apis.domain.user.User;
import com.harmony.kindless.user.repository.UserRepository;
import com.harmony.kindless.user.service.UserAuthorityService;
import com.harmony.kindless.user.service.UserService;
import com.harmony.umbrella.data.Selections;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return queryWith()
                .equal("username", username)
                .getSingleResult()
                .orElse(null);
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

    private UserDetails buildUserDetails(User user) {
        // @formatter:off
        List<SimpleGrantedAuthority> authorities = userAuthorityService.getUserAuthorities(user.getId())
                                                                       .stream()
                                                                       .map(SimpleGrantedAuthority::new)
                                                                       .collect(Collectors.toList());
        // @formatter:on
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
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
