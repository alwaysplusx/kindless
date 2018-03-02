package com.harmony.kindless.test.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.harmony.kindless.test.domain.Order;
import com.harmony.kindless.test.repository.OrderRepository;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.data.query.QueryFeature;
import com.harmony.umbrella.log.annotation.Module;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleQuery;
import com.harmony.umbrella.web.method.annotation.BundleView;
import com.harmony.umbrella.web.method.support.ViewFragment;

/**
 * @author wuxii@foxmail.com
 */
@Module("Order")
@BundleController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepo;

    @GetMapping("/list")
    @RequiresPermissions("order:list")
    @BundleQuery(feature = QueryFeature.FULL_TABLE_QUERY)
    public List<Order> list(QueryBundle<Order> bundle) {
        return orderRepo.query(bundle).getResultList();
    }

    @PostMapping({ "/save", "/add" })
    @RequiresPermissions("order:create")
    public Order create(Order order) {
        order = orderRepo.save(order);
        return order;
    }

    @BundleView
    @GetMapping("/page")
    @RequiresPermissions("order:page")
    public Page<Order> page(QueryBundle<Order> bundle) {
        return orderRepo.query(bundle).getResultPage();
    }

    @GetMapping("/view")
    @RequiresPermissions("order:view")
    public View view(QueryBundle<Order> bundle, ViewFragment vf) {
        return vf.toView(orderRepo.query(bundle).getResultPage());
    }

    @RequestMapping("/test")
    @RequiresPermissions("order:test")
    public String test(HttpServletRequest request) throws IOException {
        return "success";
    }

}
