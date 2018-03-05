package com.harmony.kindless.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.core.domain.Permission;
import com.harmony.kindless.core.repository.PermissionRepository;
import com.harmony.kindless.core.service.PermissionService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class PermissionServiceImpl extends ServiceSupport<Permission, String> implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    protected QueryableRepository<Permission, String> getRepository() {
        return permissionRepository;
    }
    //
    // public void init() {
    // permissionRepository.deleteAll();
    // Class[] classes = ApplicationContext.getApplicationClasses(new
    // PermissionClassFilter());
    // for (Class clazz : classes) {
    // ModulePermission modulePermission = new ModulePermission(clazz);
    // saveModulePermission(modulePermission);
    // }
    // }
    //
    // protected void saveModulePermission(ModulePermission modulePermission) {
    // Module menu = findMenu(modulePermission.getModule());
    // Long userId = ContextHelper.getUserId();
    // String username = ContextHelper.getUsername();
    // String nickname = ContextHelper.getNickname();
    // if (menu != null) {
    // List<MethodPermission> methodPermissions =
    // modulePermission.getMethodPermissions();
    // for (MethodPermission methodPermission : methodPermissions) {
    // Set<String> permissions = methodPermission.getPermissions();
    // for (String code : permissions) {
    // Permission permission = new Permission();
    // permission.setCode(code);
    // permission.setCreatorId(userId);
    // permission.setCreatorCode(username);
    // permission.setCreatorName(nickname);
    // permission.setCreatedTime(new Date());
    // permission.setMenu(menu);
    // saveOrUpdate(permission);
    // }
    // }
    // }
    // }
    //
    // private Module findMenu(String module) {
    // return module == null ? null : menuService.findOne(new
    // JpaQueryBuilder<>(Module.class).equal("code", module).bundle());
    // }
    //
    // private static class PermissionClassFilter implements ClassFilter {
    //
    // @Override
    // public boolean accept(Class<?> c) {
    // return ClassFilterFeature.NEWABLE.accept(c) &&
    // c.getSimpleName().endsWith("Controller");
    // }
    //
    // }
    //
    // public static class ModulePermission {
    //
    // private Class<?> clazz;
    //
    // //
    // private String module;
    // private List<MethodPermission> methodPermissions;
    //
    // public ModulePermission(Class<?> clazz) {
    // this.clazz = clazz;
    // }
    //
    // public String getModule() {
    // if (this.module == null) {
    // Module ann = clazz.getAnnotation(Module.class);
    // this.module = ann == null ? clazz.getSimpleName() : ann.value();
    // }
    // return this.module;
    // }
    //
    // public List<MethodPermission> getMethodPermissions() {
    // if (this.methodPermissions == null) {
    // List<MethodPermission> methodPermissions = new ArrayList<>();
    // Method[] methods = clazz.getMethods();
    // for (Method method : methods) {
    // if (isPermissionMethod(method)) {
    // methodPermissions.add(new MethodPermission(method));
    // }
    // }
    // this.methodPermissions = methodPermissions;
    // }
    // return this.methodPermissions;
    // }
    //
    // private boolean isPermissionMethod(Method method) {
    // return !ReflectionUtils.isObjectMethod(method) && //
    // (method.getAnnotation(RequiresPermissions.class) != null ||
    // method.getAnnotation(RequiresRoles.class) != null);
    // }
    //
    // @Override
    // public String toString() {
    // return "ModulePermission {module: " + getModule() + ", methodPermissions:
    // " + getMethodPermissions() + "}";
    // }
    //
    // }
    //
    // public static class MethodPermission {
    //
    // private Method method;
    // private Set<String> permissions;
    // private Set<String> roles;
    //
    // public MethodPermission(Method method) {
    // this.method = method;
    // }
    //
    // public Method getMethod() {
    // return method;
    // }
    //
    // public Set<String> getPermissions() {
    // if (this.permissions == null) {
    // Set<String> permissions = new LinkedHashSet<>();
    // RequiresPermissions ann =
    // method.getAnnotation(RequiresPermissions.class);
    // if (ann != null) {
    // Collections.addAll(permissions, ann.value());
    // }
    // this.permissions = permissions;
    // }
    // return permissions;
    // }
    //
    // public Set<String> getRoles() {
    // if (this.roles == null) {
    // Set<String> roles = new LinkedHashSet<>();
    // RequiresRoles ann = method.getAnnotation(RequiresRoles.class);
    // if (ann != null) {
    // Collections.addAll(roles, ann.value());
    // }
    // this.roles = roles;
    // }
    // return roles;
    // }
    //
    // @Override
    // public String toString() {
    // return "{permissions: " + getPermissions() + ", roles: " + getRoles() +
    // "}";
    // }
    //
    // }

}
