package com.kindless.core.auditor;

import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuxin
 */
@Getter
@Builder(setterPrefix = "set")
public class Auditor {

    private final Long userId;
    private final String username;
    private final Long tenantId;
    private final Map<String, Object> properties;

    public static Auditor getCurrent() {
        return AuditorHolder.HOLDER.get();
    }

    public static void setCurrent(Auditor auditor) {
        if (auditor != null) {
            AuditorHolder.HOLDER.set(auditor);
        } else {
            AuditorHolder.HOLDER.remove();
        }
    }

    public Map<String, Object> getProperties() {
        return properties == null ? Collections.emptyMap() : Collections.unmodifiableMap(properties);
    }

    private static class AuditorHolder {
        private static final ThreadLocal<Auditor> HOLDER = new InheritableThreadLocal<>();
    }

    public static class AuditorBuilder {

        public AuditorBuilder setProperty(String key, Object value) {
            if (this.properties == null) {
                this.properties = new HashMap<>();
            }
            this.properties.put(key, value);
            return this;
        }

    }

}
