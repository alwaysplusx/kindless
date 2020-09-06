package com.kindless.moment.controller;

import com.kindless.client.feign.user.UserFeignClient;
import com.kindless.core.WebResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxii
 */
@Slf4j
@RequestMapping("/moment")
@RestController
@RequiredArgsConstructor
public class MomentController {

    private final UserFeignClient userFeignClient;

    @GetMapping("/timeline")
    public WebResponse<String> timeline() {

        return null;
    }

}
