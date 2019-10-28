package com.kindless.side.repository;

import com.kindless.apis.domain.history.VisitorRecord;
import com.harmony.umbrella.data.repository.QueryableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface VisitorRecordRepository extends QueryableRepository<VisitorRecord, Long> {
}
