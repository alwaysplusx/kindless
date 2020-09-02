package com.kindless.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxin
 */
@Slf4j
@RestController
public class IndexController {

    @RequestMapping("/discard")
    public String discard() {
        log.info("discard message");
        return "";
    }

}
