package com.kindless.provider.user.repository;

import com.harmony.umbrella.data.repository.QueryableRepository;
import com.kindless.provider.user.domain.User;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author wuxii
 */
@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends QueryableRepository<User, Long> {

    User findByUsername(@Param("username") String username);

}
