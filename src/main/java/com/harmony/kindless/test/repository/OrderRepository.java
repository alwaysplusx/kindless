package com.harmony.kindless.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harmony.kindless.test.domain.Order;

/**
 * @author wuxii@foxmail.com
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}
