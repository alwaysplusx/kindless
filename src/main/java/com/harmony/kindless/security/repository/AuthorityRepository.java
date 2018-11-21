package com.harmony.kindless.security.repository;

import com.harmony.kindless.apis.domain.security.Authority;
import com.harmony.umbrella.data.repository.QueryableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface AuthorityRepository extends QueryableRepository<Authority, Long> {
}
