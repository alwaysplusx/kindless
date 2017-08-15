package com.harmony.kindless.domain.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
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
import com.harmony.kindless.util.SecurityUtils.LoginCallback;
import com.harmony.umbrella.web.controller.Response;
import com.harmony.umbrella.web.method.annotation.BundleView;
import com.harmony.umbrella.web.util.WebUtils;

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
    @RequestMapping("/login")
    public Response login(@RequestBody User user) {

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

        com.harmony.kindless.util.SecurityUtils.login(token, new LoginCallback() {

            @Override
            public void run(Subject subject, AuthenticationToken token, Exception exception) {
                // login success so create json web token
                String jwt = Jwts.builder()//
                        .setAudience(user.getUsername())//
                        .setIssuedAt(new Date())//
                        .setIssuer("kindless")//
                        .signWith(SignatureAlgorithm.HS512, user.getPassword())//
                        .compact();
                WebToken webToken = new WebToken();
                WebUtils.applyCreatorInfoIfNecessary(webToken);

                webToken.setUsername(user.getUsername());
                webToken.setWebToken(jwt);
                webToken.setSecretKey(user.getPassword());

                webTokenService.saveOrUpdate(webToken);
            }
        });
        // FIXME JWT TOKEN
        return Response.successBuilder()//
                .param("token", null)//
                .build();
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return "redirect:/login";
    }

    @RequestMapping("/menus")
    @BundleView({ "*.permissions", "*.parent" })
    public List<Menu> menus() {
        return menuService.findAll();
    }

}
