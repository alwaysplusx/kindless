package com.harmony.kindless.core.service;

import com.harmony.kindless.core.domain.User;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii@foxmail.com
 */
public interface UserService extends Service<User, Long> {

    User findByUsername(String username);

}
