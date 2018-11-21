package com.harmony.kindless.user.service;

import com.harmony.kindless.apis.domain.user.User;
import com.harmony.umbrella.data.service.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

/**
 * @author wuxii
 */
public interface UserService extends Service<User, Long>, UserDetailsService {

    Optional<User> getByUsername(String username);

}
