package com.kindless.moment.repository;

import com.kindless.moment.domain.Moment;
import com.harmony.umbrella.data.repository.QueryableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface MomentRepository extends QueryableRepository<Moment, Long> {


}
