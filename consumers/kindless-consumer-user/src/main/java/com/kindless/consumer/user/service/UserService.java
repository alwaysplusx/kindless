package com.kindless.consumer.user.service;

import com.harmony.umbrella.data.service.Service;
import com.kindless.provider.user.domain.User;

/**
 * @author wuxii
 */
public interface UserService extends Service<User, Long> {

    User getByUsername(String username);

}
