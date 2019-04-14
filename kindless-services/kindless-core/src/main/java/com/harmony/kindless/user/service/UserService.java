package com.harmony.kindless.user.service;

import com.harmony.kindless.apis.domain.user.User;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii
 */
public interface UserService extends Service<User, Long> {

    User getByUsername(String username);

}
