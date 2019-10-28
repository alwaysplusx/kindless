package com.kindless.user.user.repository;

import org.springframework.stereotype.Repository;

import com.kindless.user.domain.UserAccount;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 *
 * @author wuxii
 */
@Repository
public interface UserAccountRepository extends QueryableRepository<UserAccount, Long> {

}
