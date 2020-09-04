package com.kindless.user.service.impl;

import com.kindless.core.service.ServiceSupport;
import com.kindless.domain.user.User;
import com.kindless.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxii
 */
@Service
@RestController
public class UserServiceImpl extends ServiceSupport<User, Long> implements UserService {

    @Override
    public String echo(String name) {
        return "hi " + name;
    }

}
