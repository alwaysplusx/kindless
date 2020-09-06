package com.kindless.user.service;


import com.kindless.core.service.Service;
import com.kindless.domain.user.UserAccount;

/**
 * @author wuxii
 */
public interface UserAccountService extends Service<UserAccount> {

    UserAccount bind(Long userId, String openId);

}
