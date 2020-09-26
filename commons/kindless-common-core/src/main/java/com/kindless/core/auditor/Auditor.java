package com.kindless.core.auditor;

import lombok.Builder;
import lombok.Getter;

import java.util.*;

/**
 * @author wuxin
 */
@Getter
@Builder(setterPrefix = "set")
public class Auditor {

    private final Long userId;
    private final String username;
    private final Long tenantId;
    private final List<String> roles;
    private final List<String> permissions;
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

    public List<String> getPermissions() {
        return permissions == null ? Collections.emptyList() : Collections.unmodifiableList(permissions);
    }

    public List<String> getRoles() {
        return roles == null ? Collections.emptyList() : Collections.unmodifiableList(roles);
    }

    public Map<String, Object> getProperties() {
        return properties == null ? Collections.emptyMap() : Collections.unmodifiableMap(properties);
    }

    private static class AuditorHolder {
        private static final ThreadLocal<Auditor> HOLDER = new InheritableThreadLocal<>();
    }

    public static class AuditorBuilder {

        public AuditorBuilder addRole(String... roles) {
            if (this.roles == null) {
                this.roles = new ArrayList<>();
            }
            Collections.addAll(this.roles, roles);
            return this;
        }

        public AuditorBuilder addPermission(String... permissions) {
            if (this.permissions == null) {
                this.permissions = new ArrayList<>();
            }
            Collections.addAll(this.permissions, permissions);
            return this;
        }

        public AuditorBuilder setProperty(String key, Object value) {
            if (this.properties == null) {
                this.properties = new LinkedHashMap<>();
            }
            this.properties.put(key, value);
            return this;
        }

    }

}
