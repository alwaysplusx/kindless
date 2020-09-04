package com.kindless.user.repository;

import com.kindless.domain.user.Authority;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface AuthorityRepository extends PagingAndSortingRepository<Authority, Long> {
}
