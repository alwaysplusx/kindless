package com.harmony.kindless.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harmony.kindless.test.domain.Order;
import com.harmony.kindless.test.repository.OrderRepository;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.web.bind.annotation.RequestQueryBundle;
import com.harmony.umbrella.web.bind.annotation.RequestQueryBundle.Junction;

/**
 * @author wuxii@foxmail.com
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepo;

    @GetMapping("/list")
    public String list(@RequestQueryBundle(bundle = Junction.CONJUNCTION) QueryBundle<Order> bundle) {
        List<Order> list = orderRepo.query(bundle).getResultList();
        return list.toString();
    }

    @GetMapping("/save")
    public String save(Order order) {
        orderRepo.save(order);
        return "success";
    }

}
