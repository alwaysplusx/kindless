package com.harmony.kindless.user.controller;

import com.harmony.kindless.apis.domain.user.User;
import com.harmony.kindless.apis.util.RandomUtils;
import com.harmony.kindless.generate.WorkRunner;
import com.harmony.kindless.user.service.UserService;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wuxii
 */
@BundleController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private WorkRunner worker;

    @BundleView
    @GetMapping("/generate")
    public User generate() {
        return userService.saveOrUpdate(RandomUtils.randomUser());
    }

    @ResponseBody
    @GetMapping("/start")
    public synchronized String start(@RequestParam(defaultValue = "50") int size) {
        if (worker == null) {
            this.worker = new WorkRunner(size, this::generate);
            this.worker.start();
        }
        return "ok";
    }

    @ResponseBody
    @GetMapping("/stop")
    public synchronized String stop() {
        if (worker != null) {
            this.worker.stop();
            this.worker = null;
        }
        return "ok";
    }

}
