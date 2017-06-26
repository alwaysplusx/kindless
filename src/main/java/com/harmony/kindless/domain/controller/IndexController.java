package com.harmony.kindless.domain.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.harmony.kindless.domain.domain.Menu;
import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.service.MenuService;
import com.harmony.umbrella.web.controller.Response;
import com.harmony.umbrella.web.method.annotation.BundleView;

/**
 * 
 * @author wuxii@foxmail.com
 */
@Controller
@RequestMapping
public class IndexController {

    @Autowired
    private MenuService menuService;

    /* 
     * 前端路由 vue-router
     * @see https://router.vuejs.org/zh-cn/essentials/history-mode.html
     * @see https://segmentfault.com/q/1010000008650968/a-1020000008655937
     */
    @GetMapping({ "", "index", "/home" })
    public String index() {
        return "index.html";
    }

    @BundleView
    @GetMapping("/menus")
    public List<Menu> menus() {
        return menuService.findAll();
    }

    @ResponseBody
    @PostMapping("/login")
    public Response login(@RequestBody User user) {
        return null;
        // FIXME
        /*if (user == null) {
            return Response.error(10001, "user not provided");
        }
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return Response.error(10002, "user areadly login");
        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            subject.login(token);
            return Response.ok();
        } catch (AuthenticationException e) {
            return Response.error(10003, e.getMessage());
        }*/
    }

    @GetMapping("/logout")
    public Response logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return null;
        // return Response.ok();
    }

}
