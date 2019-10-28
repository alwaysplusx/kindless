package com.kindless.user.user.repository;

import org.springframework.stereotype.Repository;

import com.kindless.apis.domain.user.UserDetails;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii
 */
@Repository
public interface UserDetailsRepository extends QueryableRepository<UserDetails, Long> {

}
