package com.harmony.kindless.user.service;

import com.harmony.kindless.apis.domain.user.UserAccount;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii
 */
public interface UserAccountService extends Service<UserAccount, Long> {

    UserAccount bind(Long userId, String openId);

}
