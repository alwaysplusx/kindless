package com.kindless.user.repository;

import com.kindless.user.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(@Param("username") String username);

}
