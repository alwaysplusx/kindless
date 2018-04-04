package com.harmony.kindless.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.harmony.kindless.core.domain.Certificate;
import com.harmony.kindless.core.domain.Menu;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.service.MenuService;
import com.harmony.kindless.core.service.UserService;
import com.harmony.kindless.core.support.SecurityService;
import com.harmony.kindless.jwt.RequestOriginProperties;
import com.harmony.kindless.util.SecurityUtils;
import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleView;

/**
 * @author wuxii@foxmail.com
 */
@BundleController
@RequestMapping
public class IndexController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;

    @BundleView
    @RequestMapping("/login")
    public Response login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        UsernamePasswordToken upt = new UsernamePasswordToken(username, password);
        SecurityUtils.getSubject().login(upt);
        User user = userService.findByUsername(username);
        Certificate certificate = securityService.grant(user, new RequestOriginProperties(request));
        return Response.okBuilder()//
                .param("token", certificate.getToken())//
                .build();
    }

    @RequestMapping("/logout")
    public Response logout() {
        org.apache.shiro.SecurityUtils.getSubject().logout();
        return Response.ok();
    }

    @RequestMapping("/menus")
    @BundleView({ "*.permissions", "*.parent" })
    public List<Menu> menus() {
        return menuService.findAll();
    }

}
