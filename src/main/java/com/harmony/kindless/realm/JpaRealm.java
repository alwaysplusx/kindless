package com.harmony.kindless.realm;

import java.util.LinkedHashSet;
import java.util.List;
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
    private UserRepository userRepo;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        final UsernamePasswordToken credentials = (UsernamePasswordToken) token;
        final String username = credentials.getUsername();
        if (username == null) {
            throw new UnknownAccountException("username not provided");
        }
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException("username does not exists");
        }
        return new SimpleAuthenticationInfo(username, user.getPassword().toCharArray(), ByteSource.Util.bytes(username), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        final String username = (String) principals.getPrimaryPrincipal();
        final User user = userRepo.findByUsername(username);
        List<Role> roles = user.getRoles();
        final Set<String> roleNames = new LinkedHashSet<>(roles.size());
        final Set<String> permissionNames = new LinkedHashSet<>();
        if (!roles.isEmpty()) {
            for (Role role : roles) {
                roleNames.add(role.getName());
                for (Permission permission : role.getPermissions()) {
                    permissionNames.add(permission.getName());
                }
            }
        }
        final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissionNames);
        return info;
    }

}