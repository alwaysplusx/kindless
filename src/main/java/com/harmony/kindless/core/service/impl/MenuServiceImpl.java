package com.harmony.kindless.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.stereotype.Service;

import com.harmony.kindless.core.domain.Menu;
import com.harmony.kindless.core.repository.MenuRepository;
import com.harmony.kindless.core.service.MenuService;
import com.harmony.umbrella.data.query.JpaQueryBuilder;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class MenuServiceImpl extends ServiceSupport<Menu, String> implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    /**
     * 获取根菜单树
     * 
     * @return menu tree
     */
    @Override
    public Menu getRootMenuAsTree() {
        return getMenuAsTree("Root");
    }

    /**
     * 获取树形结构的菜单树
     * 
     * @param code
     *            菜单的id
     * @return
     */
    @Override
    public Menu getMenuAsTree(String code) {
        Menu menu = findById(code);
        if (menu != null) {
            cascadeChildren(menu);
        }
        return menu;
    }

    /**
     * 加载code下的子菜单
     * 
     * @param code
     *            parent code
     * @return children menu
     */
    @Override
    public List<Menu> getChildren(String code) {
        QueryBundle<Menu> bundle = new JpaQueryBuilder<Menu>()//
                .from(Menu.class)//
                .equal("parent.code", code)//
                .asc("ordinal", NullHandling.NULLS_LAST)//
                .asc("code")//
                .bundle();
        return menuRepository.query(bundle).getResultList();
    }

    /**
     * 级联加载子菜单
     * 
     * @param menus
     *            需要级联的菜单
     */
    private void cascadeChildren(Menu menu) {
        List<Menu> children = getChildren(menu.getCode());
        if (children.isEmpty()) {
            menu.setChildren(null);
        } else {
            for (Menu child : children) {
                cascadeChildren(child);
            }
            menu.setChildren(children);
        }
    }

    @Override
    protected QueryableRepository<Menu, String> getRepository() {
        return menuRepository;
    }

}
