package com.harmony.kindless.shiro.realm;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.harmony.kindless.domain.domain.Permission;
import com.harmony.kindless.domain.domain.Role;
import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.repository.UserRepository;

/**
 * @author wuxii@foxmail.com
 */
@Component
public class JpaRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        final UsernamePasswordToken credentials = (UsernamePasswordToken) token;
        final String username = credentials.getUsername();
        if (username == null) {
            throw new UnknownAccountException("username not provided");
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException("username does not exists");
        }
        return new SimpleAuthenticationInfo(username, user.getPassword().toCharArray(), ByteSource.Util.bytes(username), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        final SimpleAuthorizationInfo result = new SimpleAuthorizationInfo();

        final User user = userRepository.findByUsername((String) principals.getPrimaryPrincipal());
        final Set<String> roles = new LinkedHashSet<>(user.getRoles().size());
        final Set<String> permissions = new LinkedHashSet<>();

        if (!user.getRoles().isEmpty()) {
            for (Role role : user.getRoles()) {
                roles.add(role.getName());
                for (Permission permission : role.getPermissions()) {
                    permissions.add(permission.getName());
                }
            }
        }

        result.setRoles(roles);
        result.setStringPermissions(permissions);

        return result;
    }

}