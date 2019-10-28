package com.kindless.user.user.repository;

import com.kindless.apis.domain.user.User;
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
