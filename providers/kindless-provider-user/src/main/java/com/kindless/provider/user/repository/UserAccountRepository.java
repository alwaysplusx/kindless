package com.kindless.provider.user.repository;

import com.harmony.umbrella.data.repository.QueryableRepository;
import com.kindless.provider.user.domain.UserAccount;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface UserAccountRepository extends QueryableRepository<UserAccount, Long> {

}
