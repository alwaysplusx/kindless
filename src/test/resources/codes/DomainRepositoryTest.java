package com.harmony.kindless.domain;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.harmony.kindless.core.domain.Module;
import com.harmony.kindless.core.repository.ModuleRepository;

/**
 * @author wuxii@foxmail.com
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DomainRepositoryTest {

    @Autowired
    private ModuleRepository menuRepo;

    @Test
    public void testMenu() {
        Module menu = new Module();
        menu.setCode("top");
        menu.setName("顶级菜单");
        menu.setIcon("icon-home");
        menu.setPath("/");

        Module m1 = new Module("level1-1", "一级一", "/level1/1", "icon-1");
        Module m2 = new Module("level1-2", "一级二", "/level1/2", "icon-2");

        m1.setParent(menu);
        m2.setParent(menu);

        menu.setChildren(Arrays.asList(m1, m2));

        menuRepo.save(menu);
    }

}
