package com.kindless.user.service;

import com.kindless.core.service.Service;
import com.kindless.domain.user.User;
import com.kindless.domain.user.UserAccount;

/**
 * @author wuxii
 */
public interface UserAccountService extends Service<UserAccount> {

    User findUserByAccount(String account, int type);

    UserAccount bind(User user, UserAccount account);

}
