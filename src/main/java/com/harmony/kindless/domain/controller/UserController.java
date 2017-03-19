package com.harmony.kindless.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.repository.UserRepository;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.web.bind.annotation.BundleController;
import com.harmony.umbrella.web.bind.annotation.RequestBundle;

/**
 * @author wuxii@foxmail.com
 */
@BundleController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping({ "", "/", "/index" })
    public String index() {
        return "domain/users.html";
    }

    @GetMapping("/page")
    @RequestBundle(page = 0, size = 20)
    public Page<User> all(QueryBundle<User> bundle) {
        return userRepository.getResultPage(bundle);
    }

    @GetMapping("/add")
    public User save(User user) {
        user = userRepository.save(user);
        return user;
    }

}
