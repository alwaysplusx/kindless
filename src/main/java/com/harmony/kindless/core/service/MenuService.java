package com.harmony.kindless.core.service;

import java.util.List;

import com.harmony.kindless.core.domain.Menu;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii@foxmail.com
 */
public interface MenuService extends Service<Menu, String> {

    Menu getRootMenuAsTree();

    Menu getMenuAsTree(String code);

    List<Menu> getChildren(String code);

}
