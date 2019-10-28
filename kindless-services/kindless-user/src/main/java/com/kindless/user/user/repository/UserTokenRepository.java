package com.kindless.user.user.repository;

import org.springframework.stereotype.Repository;

import com.kindless.user.domain.UserToken;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii
 */
@Repository
public interface UserTokenRepository extends QueryableRepository<UserToken, Long> {

}
