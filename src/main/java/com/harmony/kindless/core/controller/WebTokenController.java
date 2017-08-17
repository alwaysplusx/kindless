package com.harmony.kindless.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harmony.kindless.core.domain.WebToken;
import com.harmony.kindless.core.service.WebTokenService;
import com.harmony.umbrella.data.query.QueryBundle;

/**
 * @author wuxii@foxmail.com
 */
@Controller
@RequestMapping("/jwt")
public class WebTokenController {

    @Autowired
    private WebTokenService webTokenService;

    @RequestMapping("/list")
    public List<WebToken> list(QueryBundle<WebToken> bundle) {
        return webTokenService.findList(bundle);
    }

}
