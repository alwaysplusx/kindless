package com.kindless.user.user.service.impl;

import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import com.kindless.user.domain.UserBalance;
import com.kindless.user.user.repository.UserBalanceRepository;
import com.kindless.user.user.service.UserBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author wuxin
 */
@Service
@RequiredArgsConstructor
public class UserBalanceServiceImpl extends ServiceSupport<UserBalance, Long> implements UserBalanceService {

    private final UserBalanceRepository userBalanceRepository;

    @Override
    protected QueryableRepository<UserBalance, Long> getRepository() {
        return userBalanceRepository;
    }

    @Override
    protected Class<UserBalance> getDomainClass() {
        return UserBalance.class;
    }

}
