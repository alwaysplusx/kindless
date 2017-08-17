package com.harmony.kindless.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.service.UserService;
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
    @BundleView(excludes = "roles")
    public Page<User> page(QueryBundle<User> bundle) {
        return userService.findPage(bundle);
    }

    @PostMapping({ "/create", "/save", "/update" })
    public User create(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    @GetMapping("/view/{username}")
    public User view(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Response delete(@RequestBody List<String> usernames) {
        userService.deleteAll(usernames);
        return Response//
                .successBuilder()//
                .build();
    }

}
