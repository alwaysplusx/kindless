package com.harmony.kindless.authz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.harmony.kindless.authz.domain.User;

/**
 * @author wuxii@foxmail.com
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select o from User o where username=?")
    User findByUsername(String username);

}
