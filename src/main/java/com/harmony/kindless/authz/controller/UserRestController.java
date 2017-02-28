package com.harmony.kindless.authz.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harmony.kindless.authz.domain.User;
import com.harmony.kindless.authz.repository.UserRepository;
import com.harmony.umbrella.data.query.QueryBundle;

/**
 * @author wuxii@foxmail.com
 */
@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getUsers(QueryBundle<User> bundle) {
        return userRepository.findAll();
    }

    @RequiresRoles("R1")
    @PostMapping("/create")
    public User save(@RequestBody User user) {
        user = userRepository.save(user);
        return user;
    }

}
