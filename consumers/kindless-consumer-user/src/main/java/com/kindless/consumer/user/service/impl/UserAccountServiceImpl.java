package com.kindless.consumer.user.service.impl;

import com.harmony.kindless.core.CodeException;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import com.kindless.provider.user.domain.User;
import com.kindless.provider.user.domain.UserAccount;
import com.kindless.provider.user.repository.UserAccountRepository;
import com.kindless.consumer.user.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxii
 */
@Service
public class UserAccountServiceImpl extends ServiceSupport<UserAccount, Long> implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserAccount bind(Long userId, String openId) {
        boolean exists = queryWith()
                .equal("type", UserAccount.TYPE_OF_WEIXIN)
                .equal("userId", userId)
                .count() > 0;
        if (exists) {
            throw new CodeException(-1, "已绑定微信账号, 请先解绑");
        }
        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(openId);
        userAccount.setUser(new User(userId));
        userAccount.setType(UserAccount.TYPE_OF_WEIXIN);
        userAccount.setStatus(UserAccount.STATUS_OF_PREPARE);
        return userAccountRepository.save(userAccount);
    }

    @Override
    protected QueryableRepository<UserAccount, Long> getRepository() {
        return userAccountRepository;
    }

    @Override
    protected Class<UserAccount> getDomainClass() {
        return UserAccount.class;
    }

}
