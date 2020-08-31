package com.kindless.user.repository;

import com.kindless.user.domain.UserAccount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface UserAccountRepository extends PagingAndSortingRepository<UserAccount, Long> {

}
