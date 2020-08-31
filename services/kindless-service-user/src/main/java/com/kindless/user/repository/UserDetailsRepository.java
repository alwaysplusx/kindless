package com.kindless.user.repository;

import com.kindless.user.domain.UserDetails;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface UserDetailsRepository extends PagingAndSortingRepository<UserDetails, Long> {

}
