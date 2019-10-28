package com.kindless.user.user.repository;

import com.kindless.user.domain.Client;
import com.harmony.umbrella.data.repository.QueryableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface ClientRepository extends QueryableRepository<Client, Long> {

}
