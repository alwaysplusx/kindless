package com.kindless.user.user.repository;

import com.kindless.apis.domain.user.UserAuthority;
import com.harmony.umbrella.data.repository.QueryableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface UserAuthorityRepository extends QueryableRepository<UserAuthority, Long> {
}
