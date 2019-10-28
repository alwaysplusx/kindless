package com.kindless.user.user.service;

import com.kindless.user.domain.UserAccount;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii
 */
public interface UserAccountService extends Service<UserAccount, Long> {

    UserAccount bind(Long userId, String openId);

}
