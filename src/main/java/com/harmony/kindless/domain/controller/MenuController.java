package com.harmony.kindless.domain.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.harmony.kindless.domain.domain.Menu;
import com.harmony.kindless.domain.service.MenuService;
import com.harmony.umbrella.data.query.JpaQueryBuilder;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleView;

/**
 * @author wuxii@foxmail.com
 */
@BundleController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping({ "/save", "/create", "/update" })
    public Menu save(@RequestBody Menu menu) {
        return menuService.saveOrUpdate(menu);
    }

    @RequestMapping("/tree")
    @BundleView(excludes = { "**.parent", "**.createdTime", "**.ordinal", "**.remark" })
    public List<Menu> getTree(@RequestParam(name = "code", required = false) String code) {
        Menu menu = null;
        if (code == null) {
            menu = menuService.getRootMenuAsTree();
        } else {
            menu = menuService.getMenuAsTree(code);
        }
        return menu == null ? Arrays.asList() : Arrays.asList(menu);
    }

    @RequestMapping("/children")
    @BundleView(excludes = { "**.children", "**.parent" })
    public List<Menu> getChildren(@RequestParam(name = "code") String code) {
        if (code == null) {
            throw new IllegalArgumentException("parent code not set");
        }
        return menuService.getChildren(code);
    }

    @RequestMapping("/options")
    @BundleView(includes = { "name", "code" })
    public List<Menu> getOptions(JpaQueryBuilder<Menu> builder) {
        QueryBundle<Menu> bundle = builder.asc("code")//
                .allowFullTableQuery()//
                .bundle();
        return menuService.findList(bundle);
    }

}
