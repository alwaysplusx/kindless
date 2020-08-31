package com.kindless.user.repository;

import com.kindless.user.domain.UserAuthority;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface UserAuthorityRepository extends PagingAndSortingRepository<UserAuthority, Long> {
}
