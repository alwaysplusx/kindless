package com.harmony.kindless.core.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.harmony.kindless.core.domain.User;

/**
 * @author wuxii@foxmail.com
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testLogin() {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/user/create", new User("wuxii", "123456"), String.class);
        System.out.println(responseEntity.getBody());

        responseEntity = restTemplate.getForEntity("/login?username={1}&password={2}", String.class, "wuxii", "123456");
        System.out.println(responseEntity.getBody());
    }

}
