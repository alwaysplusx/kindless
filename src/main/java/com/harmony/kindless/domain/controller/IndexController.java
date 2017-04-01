package com.harmony.kindless.domain.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.web.Response;

/**
 * 
 * @author wuxii@foxmail.com
 */
@Controller
@RequestMapping
public class IndexController {

    /* 
     * 前端路由 vue-router
     * @see https://router.vuejs.org/zh-cn/essentials/history-mode.html
     * @see https://segmentfault.com/q/1010000008650968/a-1020000008655937
     */
    @GetMapping({ "", "/home", "/login" })
    public String index() {
        return "index.html";
    }

    @GetMapping("/menus")
    @ResponseBody
    public String menus() {
        return null;
    }

    @ResponseBody
    @PostMapping("/login")
    public Response login(@RequestBody User user) {
        if (user == null) {
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
        }
    }

    @GetMapping("/logout")
    public Response logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return Response.ok();
    }

}
