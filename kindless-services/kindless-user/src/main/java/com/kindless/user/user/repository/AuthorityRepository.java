package com.kindless.user.user.repository;

import com.kindless.apis.domain.core.Authority;
import com.harmony.umbrella.data.repository.QueryableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface AuthorityRepository extends QueryableRepository<Authority, Long> {
}
