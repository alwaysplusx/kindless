package com.harmony.kindless.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.harmony.kindless.core.domain.User;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii@foxmail.com
 */
@Repository
public interface UserRepository extends QueryableRepository<User, Long> {

    @Query("select o from User o where username=?1")
    User findByUsername(String username);

}
