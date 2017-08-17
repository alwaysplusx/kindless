package com.harmony.kindless.domain;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.harmony.kindless.core.domain.Menu;
import com.harmony.kindless.core.repository.MenuRepository;

/**
 * @author wuxii@foxmail.com
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DomainRepositoryTest {

    @Autowired
    private MenuRepository menuRepo;

    @Test
    public void testMenu() {
        Menu menu = new Menu();
        menu.setCode("top");
        menu.setName("顶级菜单");
        menu.setIcon("icon-home");
        menu.setPath("/");

        Menu m1 = new Menu("level1-1", "一级一", "/level1/1", "icon-1");
        Menu m2 = new Menu("level1-2", "一级二", "/level1/2", "icon-2");

        m1.setParent(menu);
        m2.setParent(menu);

        menu.setChildren(Arrays.asList(m1, m2));

        menuRepo.save(menu);
    }

}
