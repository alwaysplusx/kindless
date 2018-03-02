package com.harmony.kindless.test.domain;

import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author wuxii@foxmail.com
 */
@Entity
@Table(name = "K_ORDER")
public class Order {

    @Id
    private Long id;

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "order")
    private List<OrderItem> items;

    public Order() {
    }

    public Order(long id, OrderItem... items) {
        this.id = id;
        this.items = Arrays.asList(items);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

}
