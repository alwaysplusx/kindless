package com.harmony.kindless.test.repository;

import org.springframework.stereotype.Repository;

import com.harmony.kindless.test.domain.OrderItem;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii@foxmail.com
 */
@Repository
public interface OrderItemRepository extends QueryableRepository<OrderItem, Long> {

}
