package com.harmony.kindless.domain.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.service.UserService;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.data.query.QueryFeature;
import com.harmony.umbrella.log.annotation.Module;
import com.harmony.umbrella.web.controller.Response;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleQuery;
import com.harmony.umbrella.web.method.annotation.BundleView;

/**
 * @author wuxii@foxmail.com
 */
@Module("User")
@BundleController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    @BundleQuery(feature = { QueryFeature.FULL_TABLE_QUERY })
    public List<User> list(QueryBundle<User> bundle) {
        return userService.findList(bundle);
    }

    @GetMapping("/page")
    @RequiresPermissions("user:page")
    @BundleView(excludes = "roles")
    public Page<User> page(QueryBundle<User> bundle) {
        return userService.findPage(bundle);
    }

    @RequiresPermissions("user:save")
    @PostMapping({ "/create", "/save", "/update" })
    public User save(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Response delete(@RequestBody List<String> usernames) {
        userService.deleteAll(usernames);
        return Response//
                .success()//
                .build();
    }

    @GetMapping("/view/{username}")
    public User view(@PathVariable("username") String username) {
        return userService.findOne(username);
    }

}
