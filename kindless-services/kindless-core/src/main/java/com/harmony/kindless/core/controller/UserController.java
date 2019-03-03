package com.harmony.kindless.core.controller;

import com.harmony.kindless.apis.ResponseCodes;
import com.harmony.kindless.apis.domain.core.User;
import com.harmony.kindless.core.service.UserService;
import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wuxii
 */
@BundleController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @BundleView({"password", "userSettings", "userDetails", "id", "new"})
    @GetMapping("/u/{username}")
    public Response<User> user(@PathVariable String username) {
        if (Math.random() > .5) {
            throw ResponseCodes.ERROR.toException();
        }
        User user = userService.getByUsername(username);
        return Response.ok(user);
    }

}
