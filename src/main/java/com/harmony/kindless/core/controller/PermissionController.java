package com.harmony.kindless.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.harmony.kindless.core.domain.Permission;
import com.harmony.kindless.core.service.PermissionService;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.data.query.QueryFeature;
import com.harmony.umbrella.web.controller.Response;
import com.harmony.umbrella.web.method.annotation.BundleQuery;
import com.harmony.umbrella.web.method.annotation.BundleView;

/**
 * @author wuxii@foxmail.com
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ResponseBody
    @RequestMapping("/init")
    public Response init() {
        permissionService.init();
        return Response//
                .successBuilder()//
                .param("success", true)//
                .build();
    }

    @RequestMapping("/page")
    @BundleView(includes = { "$.*", "*", "menu.module", "menu.code", "menu.name" })
    public Page<Permission> page(QueryBundle<Permission> bundle) {
        return permissionService.findPage(bundle);
    }

    @RequestMapping("/list")
    @BundleQuery(feature = QueryFeature.FULL_TABLE_QUERY)
    @BundleView(includes = { "$.*", "*", "menu.module", "menu.code", "menu.name" })
    public List<Permission> list(QueryBundle<Permission> bundle) {
        return permissionService.findList(bundle);
    }

}
