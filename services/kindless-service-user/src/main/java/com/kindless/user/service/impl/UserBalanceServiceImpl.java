package com.kindless.user.service.impl;

import com.kindless.core.service.ServiceSupport;
import com.kindless.domain.user.UserBalance;
import com.kindless.user.repository.UserBalanceRepository;
import com.kindless.user.service.UserBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 * @author wuxin
 */
@Service
@RequiredArgsConstructor
public class UserBalanceServiceImpl extends ServiceSupport<UserBalance> implements UserBalanceService {

    private final UserBalanceRepository userBalanceRepository;

    @Override
    protected PagingAndSortingRepository<UserBalance, Long> getRepository() {
        return userBalanceRepository;
    }

}
