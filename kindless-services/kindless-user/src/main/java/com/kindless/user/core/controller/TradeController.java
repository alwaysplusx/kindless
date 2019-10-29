package com.kindless.user.core.controller;

import com.harmony.umbrella.web.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxin
 */
@RestController
@RequestMapping
public class TradeController {

    @GetMapping("/trade_no")
    public Response<String> generateTradeNo() {
        return Response.ok();
    }

}
