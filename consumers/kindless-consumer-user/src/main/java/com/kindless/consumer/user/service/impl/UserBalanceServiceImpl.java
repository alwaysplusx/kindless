package com.kindless.consumer.user.service.impl;

import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import com.kindless.provider.user.domain.UserBalance;
import com.kindless.provider.user.repository.UserBalanceRepository;
import com.kindless.consumer.user.service.UserBalanceService;
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
