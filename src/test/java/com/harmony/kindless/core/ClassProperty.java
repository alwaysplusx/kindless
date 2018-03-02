package com.harmony.kindless.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harmony.umbrella.log.annotation.Module;

/**
 * @author wuxii@foxmail.com
 */
public class ClassProperty {

    private static final List<Class<? extends Annotation>> PATH_ANNOTATION_CLASS;

    static {
        List<Class<? extends Annotation>> annCls = new ArrayList<>();
        annCls.add(RequestMapping.class);
        annCls.add(GetMapping.class);
        annCls.add(PostMapping.class);
        annCls.add(DeleteMapping.class);
        annCls.add(PutMapping.class);
        annCls.add(PatchMapping.class);
        PATH_ANNOTATION_CLASS = Collections.unmodifiableList(annCls);
    }

    private String module;
    private Class<?> clazz;

    private Set<String> paths;
    private List<MethodProperty> methodProperties;

    public ClassProperty(Class<?> clazz) {
        this(getModule(clazz, clazz.getSimpleName()), clazz);
    }

    public ClassProperty(String module, Class<?> clazz) {
        this.module = module;
        this.clazz = clazz;
    }

    public String getModule() {
        return module;
    }

    public Class<?> getModuleClass() {
        return clazz;
    }

    public List<MethodProperty> getMethodProperties() {
        if (methodProperties == null) {
            List<MethodProperty> temp = new ArrayList<>();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                temp.add(new ClassProperty.MethodProperty(method, this));
            }
            methodProperties = temp;
        }
        return new ArrayList<>(methodProperties);
    }

    public boolean hasPath() {
        return !getPaths().isEmpty();
    }

    public Set<String> getPaths() {
        if (paths == null) {
            paths = getPaths(clazz.getAnnotation(RequestMapping.class));
        }
        return new LinkedHashSet<>(paths);
    }

    @Override
    public String toString() {
        return module + " with " + getPaths();
    }

    private static Set<String> getPaths(Method method) {
        Set<String> result = new LinkedHashSet<>();
        for (Class<? extends Annotation> annCls : PATH_ANNOTATION_CLASS) {
            Annotation ann = method.getAnnotation(annCls);
            if (ann != null) {
                Object obj = AnnotationUtils.getValue(ann);
                if (obj instanceof String[]) {
                    Collections.addAll(result, (String[]) obj);
                }
            }
        }
        return result;
    }

    private static Set<String> getPaths(RequestMapping ann) {
        Set<String> paths = new LinkedHashSet<>();
        if (ann != null) {
            Collections.addAll(paths, ann.value());
        }
        return paths;
    }

    private static Set<String> getPermissions(RequiresPermissions ann) {
        Set<String> permissions = new LinkedHashSet<>();
        if (ann != null) {
            Collections.addAll(permissions, ann.value());
        }
        return permissions;
    }

    public static String getModule(Class<?> clazz) {
        return getModule(clazz, null);
    }

    public static String getModule(Class<?> clazz, String def) {
        Module ann = clazz.getAnnotation(Module.class);
        return ann == null ? def : ann.value();
    }

    public static class MethodProperty {

        private Method method;

        private ClassProperty classProperty;

        private Set<String> resources;
        private Set<String> permissions;

        private MethodProperty(Method method, ClassProperty classProperty) {
            this.method = method;
            this.classProperty = classProperty;
        }

        public Method getMethod() {
            return method;
        }

        public boolean isObjectMethod() {
            return ReflectionUtils.isObjectMethod(method);
        }

        public List<String> getResources() {
            if (resources == null) {
                resources = getPaths(method);
            }
            return new ArrayList<>(resources);
        }

        public List<String> getPermissions() {
            if (permissions == null) {
                permissions = ClassProperty.getPermissions(method.getAnnotation(RequiresPermissions.class));
            }
            return new ArrayList<>(permissions);
        }

        public boolean hasPermission() {
            return !getPermissions().isEmpty();
        }

        public boolean hasResource() {
            return !getResources().isEmpty();
        }

        public ClassProperty getClassProperty() {
            return classProperty;
        }

        @Override
        public String toString() {
            return method.getName() + " " + getResources();
        }

    }

}
