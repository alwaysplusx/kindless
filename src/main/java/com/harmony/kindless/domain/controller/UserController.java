package com.harmony.kindless.domain.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.service.UserService;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.data.query.QueryFeature;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleQuery;

/**
 * @author wuxii@foxmail.com
 */
@BundleController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping({ "", "/", "/index" })
    public String index() {
        return "domain/users.html";
    }

    @RequestMapping("/list")
    @BundleQuery(feature = { QueryFeature.FULL_TABLE_QUERY })
    public List<User> list(QueryBundle<User> bundle) {
        return userService.findList(bundle);
    }

    @GetMapping("/page")
    public Page<User> page(QueryBundle<User> bundle) {
        return userService.findPage(bundle);
    }

    @PostMapping("/add")
    public User save(User user) {
        return userService.saveOrUpdate(user);
    }

    @ResponseBody
    @GetMapping("/delete/{username}")
    public Map<String, String> delete(@PathVariable("username") String username) {
        return Collections.emptyMap();
    }

    @GetMapping("/view/{username}")
    public User view(@PathVariable("username") String username) {
        return userService.findOne(username);
    }

}
