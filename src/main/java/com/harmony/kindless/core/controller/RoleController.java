package com.harmony.kindless.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.harmony.kindless.core.domain.Role;
import com.harmony.kindless.core.service.RoleService;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.method.annotation.BundleController;

/**
 * @author wuxii@foxmail.com
 */
@BundleController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public List<Role> getList(QueryBundle<Role> bundle) {
        return roleService.findList(bundle);
    }

    @RequestMapping({ "/save", "/create" })
    public Response create(@RequestBody Role role) {
        role = roleService.saveOrUpdate(role);
        return Response.ok();
    }

    @RequestMapping("/delete")
    public Response delete(@RequestParam("code") String code) {
        return Response.ok();
    }

}
