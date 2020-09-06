package com.kindless.user.service;

import com.kindless.client.feign.user.UserFeignClient;
import com.kindless.core.service.Service;
import com.kindless.domain.user.User;

/**
 * @author wuxii
 */
public interface UserService extends Service<User>, UserFeignClient {

}
