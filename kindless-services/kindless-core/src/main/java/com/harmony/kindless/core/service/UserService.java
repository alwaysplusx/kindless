package com.harmony.kindless.core.service;

import com.harmony.kindless.apis.domain.core.User;
import com.harmony.kindless.apis.support.RestUserDetails;
import com.harmony.umbrella.data.service.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author wuxii
 */
public interface UserService extends Service<User, Long>, UserDetailsService {

	User getByUsername(String username);

	@Override
	RestUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
