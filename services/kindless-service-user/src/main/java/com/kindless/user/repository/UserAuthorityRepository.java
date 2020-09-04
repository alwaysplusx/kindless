package com.kindless.user.repository;

import com.kindless.domain.user.UserAuthority;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface UserAuthorityRepository extends PagingAndSortingRepository<UserAuthority, Long> {
}
