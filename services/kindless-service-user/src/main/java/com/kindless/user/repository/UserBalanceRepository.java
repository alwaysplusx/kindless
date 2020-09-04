package com.kindless.user.repository;

import com.kindless.domain.user.UserBalance;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxin
 */
@Repository
public interface UserBalanceRepository extends PagingAndSortingRepository<UserBalance, Long> {
}
