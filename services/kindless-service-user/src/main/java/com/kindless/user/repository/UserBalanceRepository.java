package com.kindless.user.repository;

import com.kindless.user.domain.UserBalance;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxin
 */
@Repository
public interface UserBalanceRepository extends PagingAndSortingRepository<UserBalance, Long> {
}
