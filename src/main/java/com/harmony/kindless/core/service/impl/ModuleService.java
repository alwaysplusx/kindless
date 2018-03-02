package com.harmony.kindless.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.stereotype.Service;

import com.harmony.kindless.core.domain.Module;
import com.harmony.kindless.core.repository.ModuleRepository;
import com.harmony.umbrella.data.query.JpaQueryBuilder;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class ModuleService extends ServiceSupport<Module, String> {

    @Autowired
    private ModuleRepository menuRepository;

    @Override
    protected QueryableRepository<Module, String> getRepository() {
        return menuRepository;
    }

    /**
     * 获取根菜单树
     * 
     * @return menu tree
     */
    public Module getRootMenuAsTree() {
        return getMenuAsTree("Root");
    }

    /**
     * 获取树形结构的菜单树
     * 
     * @param code
     *            菜单的id
     * @return
     */
    public Module getMenuAsTree(String code) {
        Module menu = findOne(code);
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
    public List<Module> getChildren(String code) {
        QueryBundle<Module> bundle = new JpaQueryBuilder<Module>()//
                .from(Module.class)//
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
    private void cascadeChildren(Module menu) {
        List<Module> children = getChildren(menu.getCode());
        if (children.isEmpty()) {
            menu.setChildren(null);
        } else {
            for (Module child : children) {
                cascadeChildren(child);
            }
            menu.setChildren(children);
        }
    }
}
