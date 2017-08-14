package com.harmony.kindless.domain;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.harmony.umbrella.context.ApplicationConfigurationBuilder;
import com.harmony.umbrella.log.annotation.Module;
import com.harmony.umbrella.util.ClassFilter;
import com.harmony.umbrella.util.ClassFilterFeature;

/**
 * 
 * @author wuxii@foxmail.com
 *
 */
public class PermissionTest {

    private Class[] classes;

    @BeforeClass
    public static void beforeClass() {
        com.harmony.umbrella.context.ApplicationContext.start(ApplicationConfigurationBuilder//
                .create()//
                .addScanPackage("com.harmony")//
                .build());
    }

    @Before
    public void before() {
        classes = com.harmony.umbrella.context.ApplicationContext.getApplicationClasses(new ClassFilter() {

            @Override
            public boolean accept(Class<?> c) {
                return ClassFilterFeature.NEWABLE.accept(c) && c.getSimpleName().endsWith("Controller");
            }
        });
    }

    @Test
    public void testPermission() {
        for (Class<?> clazz : classes) {
            System.out.println(new ModulePermission(clazz));
        }
    }

    /**
     * 读取类中的权限与角色
     * 
     * @param clazz
     *            class
     * @return module permission
     */
    public ModulePermission parse(Class<?> clazz) {
        return new ModulePermission(clazz);
    }

    static class ModulePermission {

        private Class<?> clazz;

        //
        private String module;
        private List<MethodPermission> methodPermissions;

        public ModulePermission(Class<?> clazz) {
            this.clazz = clazz;
        }

        public String getModule() {
            if (this.module == null) {
                Module ann = clazz.getAnnotation(Module.class);
                this.module = ann == null ? clazz.getSimpleName() : ann.value();
            }
            return this.module;
        }

        public List<MethodPermission> getMethodPermissions() {
            if (this.methodPermissions == null) {
                List<MethodPermission> methodPermissions = new ArrayList<>();
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (isPermissionMethod(method)) {
                        methodPermissions.add(new MethodPermission(method));
                    }
                }
                this.methodPermissions = methodPermissions;
            }
            return this.methodPermissions;
        }

        private boolean isPermissionMethod(Method method) {
            return !ReflectionUtils.isObjectMethod(method) && //
                    (method.getAnnotation(RequiresPermissions.class) != null
                            || method.getAnnotation(RequiresRoles.class) != null);
        }

        @Override
        public String toString() {
            return "ModulePermission {module: " + getModule() + ", methodPermissions: " + getMethodPermissions() + "}";
        }

    }

    static class MethodPermission {

        private Method method;
        private Set<String> permissions;
        private Set<String> roles;

        public MethodPermission(Method method) {
            this.method = method;
        }

        public Method getMethod() {
            return method;
        }

        public Set<String> getPermissions() {
            if (this.permissions == null) {
                Set<String> permissions = new LinkedHashSet<>();
                RequiresPermissions ann = method.getAnnotation(RequiresPermissions.class);
                if (ann != null) {
                    Collections.addAll(permissions, ann.value());
                }
                this.permissions = permissions;
            }
            return permissions;
        }

        public Set<String> getRoles() {
            if (this.roles == null) {
                Set<String> roles = new LinkedHashSet<>();
                RequiresRoles ann = method.getAnnotation(RequiresRoles.class);
                if (ann != null) {
                    Collections.addAll(roles, ann.value());
                }
                this.roles = roles;
            }
            return roles;
        }

        @Override
        public String toString() {
            return "{permissions: " + getPermissions() + ", roles: " + getRoles() + "}";
        }

    }

}
