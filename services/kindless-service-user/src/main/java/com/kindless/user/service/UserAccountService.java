package com.kindless.user.service;


import com.kindless.core.service.Service;
import com.kindless.user.domain.UserAccount;

/**
 * @author wuxii
 */
public interface UserAccountService extends Service<UserAccount, Long> {

    UserAccount bind(Long userId, String openId);

}
