package com.harmony.kindless.core.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.harmony.kindless.core.domain.Menu;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.domain.WebToken;
import com.harmony.kindless.core.service.MenuService;
import com.harmony.kindless.core.service.WebTokenService;
import com.harmony.kindless.util.SecurityUtils;
import com.harmony.kindless.util.WebUtils;
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
    @RequestMapping("/login")
    public Response login(@RequestBody User user) {

        SecurityUtils.login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));

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

        return Response.successBuilder()//
                .param("token", jwt)//
                .build();
    }

    @ResponseBody
    @RequestMapping("/logout")
    public Response logout() {
        SecurityUtils.logout();
        return Response.successBuilder().build();
    }

    @RequestMapping("/menus")
    @BundleView({ "*.permissions", "*.parent" })
    public List<Menu> menus() {
        return menuService.findAll();
    }

}
