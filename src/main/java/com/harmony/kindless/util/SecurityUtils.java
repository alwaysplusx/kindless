package com.harmony.kindless.util;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.util.DigestUtils;

import com.harmony.kindless.context.filter.ShiroCurrentContext;
import com.harmony.kindless.core.domain.Permission;
import com.harmony.kindless.core.domain.Role;
import com.harmony.kindless.shiro.PrimaryPrincipal;
import com.harmony.umbrella.context.ApplicationContext;
import com.harmony.umbrella.context.CurrentContext;

/**
 * @author wuxii@foxmail.com
 */
public class SecurityUtils {

    public static Set<String> toRoleStringSet(Collection<Role> rs) {
        Set<String> roles = new LinkedHashSet<>();
        for (Role r : rs) {
            roles.add(r.getCode());
        }
        return roles;
    }

    public static Set<String> toPermissionStringSet(Collection<Permission> ps) {
        Set<String> permissions = new LinkedHashSet<>();
        for (Permission p : ps) {
            permissions.add(p.getCode());
        }
        return permissions;
    }

    public static PrimaryPrincipal getPrimaryPrincipal() {
        return getPrimaryPrincipal(getSubject());
    }

    public static PrimaryPrincipal getPrimaryPrincipal(Subject subject) {
        PrincipalCollection previousPrincipals = subject.getPreviousPrincipals();
        return previousPrincipals != null ? previousPrincipals.oneByType(PrimaryPrincipal.class) : null;
    }

    public static ShiroCurrentContext getCurrentContext() {
        CurrentContext cc = ApplicationContext.getCurrentContext();
        if (cc instanceof ShiroCurrentContext) {
            return (ShiroCurrentContext) cc;
        }
        return null;
    }

    public static String parseAlgorithmKey(String... s) {
        StringBuilder o = new StringBuilder();
        for (String t : s) {
            o.append(t);
        }
        return DigestUtils.md5DigestAsHex((o.toString()).getBytes());
    }

    public static Subject getSubject() {
        return org.apache.shiro.SecurityUtils.getSubject();
    }

}
