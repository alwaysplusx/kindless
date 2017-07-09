package com.harmony.kindless.domain.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.harmony.kindless.domain.domain.Menu;
import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.domain.WebToken;
import com.harmony.kindless.domain.service.MenuService;
import com.harmony.kindless.domain.service.WebTokenService;
import com.harmony.umbrella.web.controller.Response;
import com.harmony.umbrella.web.method.annotation.BundleView;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author wuxii@foxmail.com
 */
@Controller
@RequestMapping
public class IndexController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private WebTokenService webTokenService;

    @ResponseBody
    @RequestMapping("/signin")
    public Response signin(@RequestBody User user) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
        // login success so create json web token
        String webToken = Jwts.builder()//
                .setAudience(user.getUsername())//
                .setIssuedAt(new Date())//
                .setIssuer("kindless")//
                .signWith(SignatureAlgorithm.HS512, user.getPassword())//
                .compact();
        WebToken token = new WebToken();
        // token.setCreatorCode("");
        // token.setCreatorId("");
        token.setUsername(user.getUsername());
        token.setWebToken(webToken);
        token.setSecretKey(user.getPassword());
        webTokenService.saveOrUpdate(token);
        return Response.success()//
                .param("token", webToken)//
                .build();
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
