package com.kindless.moment.repository;

import com.kindless.domain.moment.Moment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface MomentRepository extends PagingAndSortingRepository<Moment, Long> {


}
