package com.kindless.core.auditor;

import java.util.Optional;

/**
 * @author wuxin
 */
public class AuditorAware implements org.springframework.data.domain.AuditorAware<Auditor> {

    @Override
    public Optional<Auditor> getCurrentAuditor() {
        return Optional.ofNullable(Auditor.getCurrent());
    }

}
