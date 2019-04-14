package com.harmony.kindless.user.repository;

import com.harmony.kindless.apis.domain.user.User;
import org.springframework.stereotype.Repository;

import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * 
 * @author wuxii
 */
@Repository
public interface UserRepository extends QueryableRepository<User, Long> {

    User findByUsername(String username);

}
