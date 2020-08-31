package com.kindless.user.repository;

import com.kindless.user.domain.Authority;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface AuthorityRepository extends PagingAndSortingRepository<Authority, Long> {
}
