package com.harmony.kindless.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harmony.kindless.domain.domain.Menu;
import com.harmony.kindless.domain.service.MenuService;
import com.harmony.umbrella.data.query.JpaQueryBuilder;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.web.method.annotation.BundleController;

/**
 * @author wuxii@foxmail.com
 */
@BundleController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/list")
    public List<Menu> list(QueryBundle<Menu> bundle) {
        return menuService.findList(bundle);
    }

    @RequestMapping("/page")
    public Page<Menu> page(QueryBundle<Menu> bundle) {
        return menuService.findPage(bundle);
    }

    @RequestMapping({ "/add", "/save" })
    public Menu save(Menu menu) {
        return menuService.saveOrUpdate(menu);
    }

    @RequestMapping("/view/{id}")
    public Menu view(@PathVariable("id") String code) {
        return menuService.findOne(code);
    }

    @RequestMapping("/tree")
    public List<Menu> tree(JpaQueryBuilder<Menu> builder) {
        QueryBundle<Menu> bundle = builder//
                .isNotNull("childs")//
                .bundle();
        return menuService.findList(bundle);
    }

}
