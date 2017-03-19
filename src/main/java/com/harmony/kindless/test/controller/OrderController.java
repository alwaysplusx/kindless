package com.harmony.kindless.test.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.harmony.kindless.test.domain.Order;
import com.harmony.kindless.test.repository.OrderRepository;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.web.bind.annotation.BundleController;
import com.harmony.umbrella.web.bind.annotation.RequestBundle;
import com.harmony.umbrella.web.bind.annotation.BundleView;
import com.harmony.umbrella.web.method.ViewFragment;

/**
 * @author wuxii@foxmail.com
 */
@BundleController
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

    @GetMapping("/page")
    @RequestBundle(page = 1, size = 20)
    @BundleView(excludes = "items[*].id")
    public Page<Order> page(QueryBundle<Order> bundle) {
        return orderRepo.query(bundle).getResultPage();
    }

    @GetMapping("/view")
    @RequestBundle(page = 1, size = 20)
    public View view(QueryBundle<Order> bundle, ViewFragment vf) {
        return vf.finish(orderRepo.query(bundle).getResultPage());
    }

    @GetMapping("/viewResolver")
    public String viewResolver(Map<String, Object> m) {
        m.put("page", new Order(1));
        return "json:page";
    }

}
