package com.kindless.core.web.bind.method;

import com.kindless.core.auditor.Auditor;
import com.kindless.core.web.bind.annotation.AuditorParam;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

/**
 * @author wuxin
 */
public class AuditorParamMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {

    private AuditorAware<Auditor> auditorAware = new com.kindless.core.auditor.AuditorAware();

    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        AuditorParam ann = parameter.getParameterAnnotation(AuditorParam.class);
        return new NamedValueInfo(ann.name(), ann.required(), null);
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        return auditorAware
                .getCurrentAuditor()
                .map(auditor -> getAuditorValue(name, auditor))
                .orElse(null);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasMethodAnnotation(AuditorParam.class);
    }

    protected Object getAuditorValue(String name, Auditor auditor) {
        return ofName(name).extract(auditor);
    }

    public void setAuditorAware(AuditorAware<Auditor> auditorAware) {
        this.auditorAware = auditorAware;
    }

    private enum AuditorValue {

        USER_ID("userId") {
            @Override
            public Object extract(Auditor auditor) {
                return auditor.getUserId();
            }
        },
        TENANT_ID("tenantId") {
            @Override
            public Object extract(Auditor auditor) {
                return auditor.getTenantId();
            }
        },
        USERNAME("username") {
            @Override
            public Object extract(Auditor auditor) {
                return auditor.getUsername();
            }
        },
        PROPERTIES("properties") {
            @Override
            public Object extract(Auditor auditor) {
                return auditor.getProperties();
            }
        };

        public String name;

        AuditorValue(String name) {
            this.name = name;
        }

        public abstract Object extract(Auditor auditor);

    }

    private static AuditorValue ofName(String name) {
        for (AuditorValue value : AuditorValue.values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException(name + " auditor value not found");
    }

}
