package com.harmony.kindless.test.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "KL_TT_ORDER_ITEM")
public class OrderItem {

    @Id
    private Long id;
    private String name;
    @ManyToOne(optional = true)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = true)
    private Order order;

    public OrderItem() {
    }

    public OrderItem(long id) {
        this.id = id;
    }

    public OrderItem(long id, Order order) {
        this.id = id;
        this.order = order;
    }

    public OrderItem(long id, String name, Order order) {
        this.id = id;
        this.name = name;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
