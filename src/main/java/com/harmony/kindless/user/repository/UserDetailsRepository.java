package com.harmony.kindless.user.repository;

import org.springframework.stereotype.Repository;

import com.harmony.kindless.apis.domain.user.UserDetails;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii
 */
@Repository
public interface UserDetailsRepository extends QueryableRepository<UserDetails, Long> {

}
