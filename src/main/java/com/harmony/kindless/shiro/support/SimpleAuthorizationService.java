package com.harmony.kindless.shiro.support;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.harmony.kindless.core.domain.Permission;
import com.harmony.kindless.core.domain.Role;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.service.PermissionService;
import com.harmony.kindless.core.service.RoleService;
import com.harmony.kindless.core.service.UserService;
import com.harmony.kindless.shiro.AuthorizationService;
import com.harmony.kindless.util.SecurityUtils;
import com.harmony.umbrella.data.query.JpaQueryBuilder;

/**
 * @author wuxii@foxmail.com
 */
public class SimpleAuthorizationService implements AuthorizationService {

    private UserService userService;
    private RoleService roleService;
    private PermissionService permissionService;

    @Override
    public User getUser(String username) {
        return userService.findByUsername(username);
    }

    @Override
    public List<Role> getRoles(String username) {
        JpaQueryBuilder<Role> builder = JpaQueryBuilder//
                .newBuilder(Role.class)//
                .equal("users.username", username);
        return roleService.findList(builder.bundle());
    }

    @Override
    public List<Permission> getPermissions(String username) {
        JpaQueryBuilder<Permission> builder = JpaQueryBuilder//
                .newBuilder(Permission.class)//
                .equal("roles.users.username", username);
        return permissionService.findList(builder.bundle());
    }

    @Override
    public AuthorizationCollection getAuthorizationCollection(Long userId) {
        User user = userService.findById(userId);
        Set<String> roles = getRoles(user);
        Set<String> permissions = getPermissions(user);
        return new AuthorizationCollection(permissions, roles);
    }

    @Override
    public AuthorizationCollection getAuthorizationCollection(String token) {
        return null;
    }

    protected Set<String> getPermissions(User user) {
        Set<String> permissions = new LinkedHashSet<>();
        List<Role> roles = getRolesAlways(user);
        for (Role role : roles) {
            if (role.getPermissions() != null) {
                permissions.addAll(SecurityUtils.toPermissionStringSet(role.getPermissions()));
            }
        }
        return permissions;
    }

    protected Set<String> getRoles(User user) {
        List<Role> roles = getRolesAlways(user);
        return SecurityUtils.toRoleStringSet(roles);
    }

    private List<Role> getRolesAlways(User user) {
        try {
            return user.getRoles();
        } catch (Exception e) {
            JpaQueryBuilder<Role> builder = JpaQueryBuilder//
                    .<Role> newBuilder()//
                    .from(Role.class)//
                    .equal("users.userId", user.getUserId());//
            return roleService.findList(builder.bundle());
        }
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public PermissionService getPermissionService() {
        return permissionService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

}
