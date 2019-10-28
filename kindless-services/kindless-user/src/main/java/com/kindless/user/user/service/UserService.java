package com.kindless.user.user.service;

import com.kindless.user.domain.User;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii
 */
public interface UserService extends Service<User, Long> {

    User getByUsername(String username);

}
