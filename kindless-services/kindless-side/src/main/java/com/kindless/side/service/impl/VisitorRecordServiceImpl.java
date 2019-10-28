package com.kindless.side.service.impl;

import com.kindless.side.domain.VisitorRecord;
import com.kindless.side.repository.VisitorRecordRepository;
import com.kindless.side.service.VisitorRecordService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxii
 */
@Service
public class VisitorRecordServiceImpl extends ServiceSupport<VisitorRecord, Long> implements VisitorRecordService {

    private final VisitorRecordRepository visitorRecordRepository;

    @Autowired
    public VisitorRecordServiceImpl(VisitorRecordRepository visitorRecordRepository) {
        this.visitorRecordRepository = visitorRecordRepository;
    }

    @Override
    protected QueryableRepository<VisitorRecord, Long> getRepository() {
        return visitorRecordRepository;
    }

    @Override
    protected Class<VisitorRecord> getDomainClass() {
        return VisitorRecord.class;
    }

}
