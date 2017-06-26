package com.harmony.kindless.domain.repository;

import org.springframework.stereotype.Repository;

import com.harmony.kindless.domain.domain.Menu;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii@foxmail.com
 */
@Repository
public interface MenuRepository extends QueryableRepository<Menu, String> {

}
