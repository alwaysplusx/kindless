package com.kindless.user.repository;

import com.kindless.user.domain.Client;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

}
