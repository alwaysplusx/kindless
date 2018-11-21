package com.harmony.kindless.user;

import com.harmony.kindless.user.service.UserAuthorityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author wuxii
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserAuthorityServiceTest {

    @Autowired
    private UserAuthorityService authorityService;

    @Test
    public void testGetAuthorities() {
        List<String> userAuthorities = authorityService.getUserAuthorities(1l);
        System.out.println(userAuthorities);
    }

}
