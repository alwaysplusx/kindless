package com.harmony.kindless.shiro;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.harmony.kindless.core.domain.Role;
import com.harmony.kindless.core.service.PermissionService;
import com.harmony.kindless.core.service.RoleService;
import com.harmony.kindless.core.service.UserService;
import com.harmony.kindless.shiro.support.SimpleAuthorizationService;

/**
 * @author wuxii@foxmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthorizationServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    private SimpleAuthorizationService authorizationService;

    @Before
    public void before() {
        authorizationService = new SimpleAuthorizationService();
        authorizationService.setPermissionService(permissionService);
        authorizationService.setUserService(userService);
        authorizationService.setRoleService(roleService);
    }

    @Test
    public void test() {
        List<Role> roles = authorizationService.getRoles("wuxii");
        System.out.println(roles);
    }

}
