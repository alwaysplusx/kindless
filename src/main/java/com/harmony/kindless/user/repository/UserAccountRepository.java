package com.harmony.kindless.user.repository;

import org.springframework.stereotype.Repository;

import com.harmony.kindless.apis.domain.user.UserAccount;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * 
 * @author wuxii
 */
@Repository
public interface UserAccountRepository extends QueryableRepository<UserAccount, Long> {

}
