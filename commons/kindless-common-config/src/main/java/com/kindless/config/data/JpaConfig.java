package com.kindless.config.data;

import com.harmony.umbrella.core.IdGenerator;
import com.harmony.umbrella.data.config.EnableDataAuditing;
import com.kindless.core.auditor.Auditor;
import com.kindless.core.data.DataAuditingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * @author wuxin
 */
@Slf4j
@Configuration
@EnableDataAuditing(auditingHandlerRef = JpaConfig.AUDITING_HANDLER_REF)
public class JpaConfig {

    public static final String AUDITING_HANDLER_REF = "auditingHandler";

    @Bean(AUDITING_HANDLER_REF)
    public DataAuditingHandler auditingHandler(IdGenerator<Long> idGenerator) {
        DataAuditingHandler handler = new DataAuditingHandler();
        handler.setAuditorAware(auditorAware());
        handler.setIdGenerator(idGenerator);
        return handler;
    }

    private AuditorAware<Auditor> auditorAware() {
        return new com.kindless.core.auditor.AuditorAware();
    }

}

