package com.harmony.kindless.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harmony.kindless.core.domain.Token;
import com.harmony.kindless.core.service.TokenService;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.data.query.QueryFeature;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleQuery;

/**
 * @author wuxii@foxmail.com
 */
@BundleController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/list")
    @BundleQuery(feature = { QueryFeature.FULL_TABLE_QUERY })
    public List<Token> list(QueryBundle<Token> bundle) {
        return tokenService.findList(bundle);
    }

}
