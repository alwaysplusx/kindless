package com.kindless.user.user.repository;

import org.springframework.stereotype.Repository;

import com.kindless.user.domain.UserDetails;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii
 */
@Repository
public interface UserDetailsRepository extends QueryableRepository<UserDetails, Long> {

}
