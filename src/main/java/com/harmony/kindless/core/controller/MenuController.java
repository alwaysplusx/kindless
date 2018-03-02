package com.harmony.kindless.core.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.harmony.kindless.core.domain.Module;
import com.harmony.kindless.core.service.impl.ModuleService;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleView;

/**
 * @author wuxii@foxmail.com
 */
@com.harmony.umbrella.log.annotation.Module("Menu")
@BundleController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private ModuleService menuService;

    @RequestMapping("/create")
    public Module create(@RequestBody Module menu) {
        return menuService.saveOrUpdate(menu);
    }

    @RequestMapping("/tree")
    @BundleView(excludes = { "**.parent", "**.createdTime", "**.ordinal", "**.remark", "**.permissions" })
    public List<Module> getTree(@RequestParam(required = false) String code) {
        Module menu = null;
        if (code == null) {
            menu = menuService.getRootMenuAsTree();
        } else {
            menu = menuService.getMenuAsTree(code);
        }
        return menu == null ? Arrays.asList() : Arrays.asList(menu);
    }

    @RequestMapping("/children")
    @BundleView(excludes = { "**.children", "**.parent" })
    public List<Module> getChildren(@RequestParam String code) {
        if (code == null) {
            throw new IllegalArgumentException("parent code not set");
        }
        return menuService.getChildren(code);
    }

    @RequestMapping("/view/{id}")
    @RequiresPermissions("menu:read")
    public Module view(@PathVariable String id) {
        return id == null ? null : menuService.findOne(id);
    }

}
