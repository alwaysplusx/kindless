package com.harmony.kindless.user.repository;

import com.harmony.kindless.apis.domain.core.Client;
import com.harmony.umbrella.data.repository.QueryableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface ClientRepository extends QueryableRepository<Client, Long> {

}