package com.harmony.kindless.domain.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harmony.kindless.domain.domain.Menu;
import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.service.MenuService;
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
    // @GetMapping({ "", "index", "/home" })
    // public String index() {
    //     return "index.html";
    // }

    @PostMapping("/login")
    public String login(User user) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        subject.login(token);
        return "redirect:/index.html";
    }

    @GetMapping("/login")
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated() ? "redirect:/index.html" : "redirect:/login.html";
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return "redirect:/login";
    }

    @BundleView
    @RequestMapping("/menus")
    public List<Menu> menus() {
        return menuService.findAll();
    }
}
