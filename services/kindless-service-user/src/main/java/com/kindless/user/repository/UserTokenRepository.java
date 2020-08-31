package com.kindless.user.repository;

import com.kindless.user.domain.UserToken;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface UserTokenRepository extends PagingAndSortingRepository<UserToken, Long> {

}
