package com.harmony.kindless.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.harmony.kindless.core.domain.Permission;
import com.harmony.kindless.core.service.impl.PermissionService;
import com.harmony.umbrella.data.query.JpaQueryBuilder;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.data.query.QueryFeature;
import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleQuery;
import com.harmony.umbrella.web.method.annotation.BundleView;

/**
 * @author wuxii@foxmail.com
 */
@BundleController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ResponseBody
    @RequestMapping({ "/init", "/reset" })
    public Response init() {
        // permissionService.init();
        return Response.ok();
    }

    @RequestMapping({ "/save", "/update" })
    @BundleView(includes = { "$.*", "*", "menu.module", "menu.code", "menu.name" })
    public Permission create(@RequestBody Permission permission) {
        return permissionService.saveOrUpdate(permission);
    }

    @RequestMapping("/page")
    @BundleView(includes = { "$.*", "*", "menu.module", "menu.code", "menu.name" })
    public Page<Permission> page(QueryBundle<Permission> bundle) {
        return permissionService.findPage(bundle);
    }

    @RequestMapping("/content")
    @BundleView(includes = { "$.*", "*", "menu.module", "menu.code", "menu.name" })
    public List<Permission> content(@RequestParam(name = "code") String code) {
        QueryBundle<Permission> bundle = new JpaQueryBuilder<>(Permission.class)//
                .equal("menu.code", code)//
                .bundle();
        return permissionService.findList(bundle);
    }

    @RequestMapping("/list")
    @BundleQuery(feature = QueryFeature.FULL_TABLE_QUERY)
    @BundleView(includes = { "$.*", "*", "menu.module", "menu.code", "menu.name" })
    public List<Permission> list(QueryBundle<Permission> bundle) {
        return permissionService.findList(bundle);
    }

}
