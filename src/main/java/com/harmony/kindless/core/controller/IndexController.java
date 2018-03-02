package com.harmony.kindless.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.harmony.kindless.core.domain.Module;
import com.harmony.kindless.core.domain.Token;
import com.harmony.kindless.core.service.SecurityService;
import com.harmony.kindless.core.service.impl.ModuleService;
import com.harmony.kindless.shiro.JwtToken;
import com.harmony.kindless.shiro.JwtToken.OriginClaims;
import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleView;

/**
 * 
 * @author wuxii@foxmail.com
 */
@BundleController
@RequestMapping
public class IndexController {

    @Autowired
    private ModuleService menuService;
    @Autowired
    private SecurityService securityService;

    @BundleView
    @RequestMapping("/login")
    public Response login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        // FIXME login first
        OriginClaims originClaims = JwtToken.createOriginClaims(request);
        Token token = securityService.login(username, password, originClaims);
        return Response.okBuilder()//
                .param("token", token.getToken())//
                .build();
    }

    @RequestMapping("/logout")
    public Response logout() {
        org.apache.shiro.SecurityUtils.getSubject().logout();
        return Response.ok();
    }

    @RequestMapping("/menus")
    @BundleView({ "*.permissions", "*.parent" })
    public List<Module> menus() {
        return menuService.findAll();
    }

}
