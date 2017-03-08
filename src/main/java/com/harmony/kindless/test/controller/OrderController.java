package com.harmony.kindless.test.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harmony.kindless.test.domain.Order;
import com.harmony.kindless.test.repository.OrderRepository;
import com.harmony.umbrella.data.query.QueryBundle;

/**
 * @author wuxii@foxmail.com
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepo;

    @GetMapping("/list")
    public String list(QueryBundle<Order> bundle) {
        List<Order> list = orderRepo.query(bundle).getResultList();
        return list.toString();
    }

    @GetMapping("/save")
    public String save(Order order) {
        orderRepo.save(order);
        return "success";
    }

    @GetMapping("/test")
    public void request(HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        context.getContextPath();
        context.getContext("/");
        System.out.println(context);
    }

}
