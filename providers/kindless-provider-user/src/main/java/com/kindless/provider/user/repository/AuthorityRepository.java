package com.kindless.provider.user.repository;

import com.harmony.umbrella.data.repository.QueryableRepository;
import com.kindless.provider.user.domain.Authority;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author wuxii
 */
@RepositoryRestResource
public interface AuthorityRepository extends QueryableRepository<Authority, Long> {
}
