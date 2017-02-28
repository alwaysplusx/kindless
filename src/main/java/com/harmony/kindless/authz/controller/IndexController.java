package com.harmony.kindless.authz.controller;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.harmony.kindless.authz.domain.User;

/**
 * 
 * @author wuxii@foxmail.com
 */
@Controller
@RequestMapping("/")
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @GetMapping
    public String index() {
        return "index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "index.html";
    }

    @ResponseBody
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        if (user == null) {
            return Collections.emptyMap();
        }
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            log.info("user {} authenticated", user.getUsername());
        }
        Map<String, Object> m = new LinkedHashMap<>();
        log.info("login {}", user);
        boolean success = false;
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
            success = true;
        } catch (AuthenticationException e) {
            success = false;
        }
        m.put("success", success);
        return m;
    }

    public void oauth() {

    }

}
