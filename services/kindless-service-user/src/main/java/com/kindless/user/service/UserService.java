package com.kindless.user.service;

import com.kindless.client.user.UserClient;
import com.kindless.core.service.Service;
import com.kindless.user.domain.User;

/**
 * @author wuxii
 */
public interface UserService extends Service<User, Long>, UserClient {


}
