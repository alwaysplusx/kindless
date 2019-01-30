package com.harmony.kindless.moment.repository;

import com.harmony.kindless.apis.domain.moment.Moment;
import com.harmony.umbrella.data.repository.QueryableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface MomentRepository extends QueryableRepository<Moment, Long> {
}
