package com.kindless.user.core.controller;

import com.harmony.umbrella.core.IdGenerator;
import com.harmony.umbrella.web.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxin
 */
@RequiredArgsConstructor
@RestController
@RequestMapping
public class TradeController {

    private final RedisTemplate<String, String> redisTemplate;

    private final IdGenerator<Long> idGenerator;

    @GetMapping("/trade_no")
    public Response<String> generateTradeNo() {
        return Response.ok();
    }

}
