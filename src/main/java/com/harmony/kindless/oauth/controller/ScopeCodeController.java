package com.harmony.kindless.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.kindless.oauth.service.ScopeCodeService;
import com.harmony.umbrella.web.bind.annotation.BundleController;

/**
 * @author wuxii@foxmail.com
 */
@BundleController
@RequestMapping("/scope")
public class ScopeCodeController {

    @Autowired
    private ScopeCodeService scopeCodeService;

    @GetMapping("/default")
    public ScopeCode defaultAppScopeCode() {
        return scopeCodeService.createScopeCode("all", ClientInfo.DEFAULT_APP);
    }

}
