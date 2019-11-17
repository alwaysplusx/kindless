package com.kindless.provider.user.repository;

import com.harmony.umbrella.data.repository.QueryableRepository;
import com.kindless.provider.user.domain.UserBalance;
import org.springframework.stereotype.Repository;

/**
 * @author wuxin
 */
@Repository
public interface UserBalanceRepository extends QueryableRepository<UserBalance, Long> {
}
