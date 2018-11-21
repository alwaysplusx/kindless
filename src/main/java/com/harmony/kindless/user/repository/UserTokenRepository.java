package com.harmony.kindless.user.repository;

import org.springframework.stereotype.Repository;

import com.harmony.kindless.apis.domain.user.UserToken;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii
 */
@Repository
public interface UserTokenRepository extends QueryableRepository<UserToken, Long> {

}
