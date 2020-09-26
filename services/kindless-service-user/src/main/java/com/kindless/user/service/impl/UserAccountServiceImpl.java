package com.kindless.user.service.impl;

import com.kindless.core.service.ServiceSupport;
import com.kindless.domain.user.User;
import com.kindless.domain.user.UserAccount;
import com.kindless.user.repository.UserAccountRepository;
import com.kindless.user.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;

/**
 * @author wuxii
 */
@RequiredArgsConstructor
@Service
public class UserAccountServiceImpl extends ServiceSupport<UserAccount> implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserAccount bind(User user, UserAccount account) {
        account.setUser(user);
        return save(account);
    }

    @Override
    public User findUserByAccount(String account, int type) {
        return userAccountRepository
                .findOne(conditionOf(account, type))
                .map(UserAccount::getUser)
                .orElse(null);
    }

    private Specification<UserAccount> conditionOf(String account, int type) {
        return (Specification<UserAccount>) (root, query, cb) -> {
            Path<String> accountPath = root.get("account");
            Path<Integer> typePath = root.get("type");
            return cb.and(cb.equal(accountPath, account), cb.equal(typePath, type));
        };
    }

    @Override
    protected PagingAndSortingRepository<UserAccount, Long> getRepository() {
        return userAccountRepository;
    }

}
