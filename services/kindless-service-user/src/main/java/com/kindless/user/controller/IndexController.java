package com.kindless.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxin
 */
@RestController
public class IndexController {

    @RequestMapping("/discard")
    public String discard() {
        return "";
    }

}
