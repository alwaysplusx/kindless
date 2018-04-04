package com.harmony.kindless.shiro;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.harmony.kindless.core.domain.Permission;
import com.harmony.kindless.core.domain.Role;
import com.harmony.kindless.core.domain.User;

/**
 * @author wuxii@foxmail.com
 */
public interface AuthorizationService {

    User getUser(String username);

    List<Role> getRoles(String username);

    List<Permission> getPermissions(String username);

    /**
     * 根据用户获取对应的授权集合
     * 
     * @param userId
     *            用户id
     * @return 用户对应的授权集合
     */
    AuthorizationCollection getAuthorizationCollection(Long userId);

    /**
     * 根据token获取对应的授权集合
     * 
     * @param token
     *            token
     * @return 授权集合
     */
    AuthorizationCollection getAuthorizationCollection(String token);

    public static class AuthorizationCollection implements Serializable {

        private static final long serialVersionUID = 6301954108274895342L;
        private final Set<String> permissions;
        private final Set<String> roles;

        public AuthorizationCollection(Set<String> roles, Set<String> permissions) {
            this.permissions = permissions;
            this.roles = roles;
        }

        public Set<String> getPermissions() {
            return permissions;
        }

        public Set<String> getRoles() {
            return roles;
        }

    }

}
