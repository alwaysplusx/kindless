package com.kindless.consumer.user.service;

import com.harmony.umbrella.data.service.Service;
import com.kindless.provider.user.domain.UserAccount;

/**
 * @author wuxii
 */
public interface UserAccountService extends Service<UserAccount, Long> {

    UserAccount bind(Long userId, String openId);

}
