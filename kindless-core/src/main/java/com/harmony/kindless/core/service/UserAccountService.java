package com.harmony.kindless.core.service;

import com.harmony.kindless.apis.domain.core.UserAccount;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii
 */
public interface UserAccountService extends Service<UserAccount, Long> {

    UserAccount bind(Long userId, String openId);

}
