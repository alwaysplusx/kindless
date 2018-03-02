package com.harmony.kindless.oauth.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.harmony.kindless.oauth.domain.ScopeCode;

/**
 * @author wuxii@foxmail.com
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ScopeCodeRepositoryTest {

    @Autowired
    private ScopeCodeRepository authorizeCodeRepository;

    @Test
    public void testFind() {
        ScopeCode authorizeCode = authorizeCodeRepository.findByCodeAndClientId("", "");
        System.out.println(authorizeCode);
    }

}
