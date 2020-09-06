package com.kindless.core.data;

import com.harmony.umbrella.core.IdGenerator;
import com.kindless.core.auditor.Auditor;
import com.kindless.core.domain.BaseEntity;
import org.springframework.data.domain.AuditorAware;

import java.util.Date;

/**
 * @author wuxin
 */
public class DataAuditingHandler implements com.harmony.umbrella.data.config.support.DataAuditingHandler<BaseEntity> {

    private IdGenerator<Long> idGenerator;
    private AuditorAware<Auditor> auditorAware;

    public DataAuditingHandler() {
    }

    public DataAuditingHandler(IdGenerator<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public void markCreated(BaseEntity source) {
        Long id = source.getId();
        if (id == null) {
            source.setId(idGenerator.generateId());
        }
        auditorAware.getCurrentAuditor()
                .ifPresent(e -> {
                    source.setCreatedBy(e.getUserId());
                    source.setCreatedAt(new Date());
                });
    }

    @Override
    public void markModified(BaseEntity source) {
        auditorAware.getCurrentAuditor()
                .ifPresent(e -> {
                    source.setUpdatedBy(e.getUserId());
                    source.setUpdatedAt(new Date());
                });
    }

    public void setAuditorAware(AuditorAware<Auditor> auditorAware) {
        this.auditorAware = auditorAware;
    }

    public void setIdGenerator(IdGenerator<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

}
