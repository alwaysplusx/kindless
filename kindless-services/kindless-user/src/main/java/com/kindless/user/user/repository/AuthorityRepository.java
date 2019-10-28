package com.kindless.user.user.repository;

import com.kindless.user.domain.Authority;
import com.harmony.umbrella.data.repository.QueryableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface AuthorityRepository extends QueryableRepository<Authority, Long> {
}
