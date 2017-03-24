package com.harmony.kindless.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.repository.UserRepository;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.web.bind.annotation.BundleController;
import com.harmony.umbrella.web.bind.annotation.BundleView;
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

    @GetMapping("/page/{page}")
    @RequestBundle(page = 0, size = 20)
    @BundleView(excludes = { "id", "new" })
    public Page<User> page(QueryBundle<User> bundle) {
        return userRepository.getResultPage(bundle);
    }

    @PostMapping("/add")
    public User save(User user) {
        return userRepository.save(user);
    }

    @GetMapping("/delete/{username}")
    public void delete(@PathVariable("username") String username) {
        userRepository.delete(username);
    }

    @GetMapping("/view/{username}")
    @BundleView(excludes = { "id", "new" })
    public User view(@PathVariable("username") String username) {
        return userRepository.findOne(username);
    }

}
