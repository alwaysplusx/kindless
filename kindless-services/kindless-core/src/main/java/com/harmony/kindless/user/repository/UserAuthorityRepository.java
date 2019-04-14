package com.harmony.kindless.user.repository;

import com.harmony.kindless.apis.domain.user.UserAuthority;
import com.harmony.umbrella.data.repository.QueryableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface UserAuthorityRepository extends QueryableRepository<UserAuthority, Long> {
}
