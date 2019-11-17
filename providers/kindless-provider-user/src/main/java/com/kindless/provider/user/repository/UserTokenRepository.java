package com.kindless.provider.user.repository;

import com.harmony.umbrella.data.repository.QueryableRepository;
import com.kindless.provider.user.domain.UserToken;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface UserTokenRepository extends QueryableRepository<UserToken, Long> {

}
