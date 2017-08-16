package com.harmony.kindless.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.harmony.kindless.domain.domain.User;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii@foxmail.com
 */
@Repository
public interface UserRepository extends QueryableRepository<User, Long> {

    @Query("select o from User o where username=?")
    User findByUsername(String username);

    @Query("select o from User o where username=?")
    User findByClientId(String clientId);

}
