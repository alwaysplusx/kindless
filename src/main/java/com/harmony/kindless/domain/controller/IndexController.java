package com.harmony.kindless.domain.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping("/signin")
    public String signin(User user) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        subject.login(token);
        return "redirect:/";
    }

    @RequestMapping("/signout")
    public String signout() {
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
