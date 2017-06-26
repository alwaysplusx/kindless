package com.harmony.kindless.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.domain.domain.Menu;
import com.harmony.kindless.domain.repository.MenuRepository;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class MenuService extends ServiceSupport<Menu, String> {

    @Autowired
    private MenuRepository menuRepo;

    @Override
    protected QueryableRepository<Menu, String> getRepository() {
        return menuRepo;
    }

}
