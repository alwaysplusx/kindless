package com.harmony.kindless.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harmony.kindless.test.domain.OrderItem;

/**
 * @author wuxii@foxmail.com
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
