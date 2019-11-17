package com.kindless.provider.user.repository;

import com.harmony.umbrella.data.repository.QueryableRepository;
import com.kindless.provider.user.domain.UserAuthority;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface UserAuthorityRepository extends QueryableRepository<UserAuthority, Long> {
}
