package com.harmony.kindless.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.service.ClientInfoService;
import com.harmony.umbrella.web.method.annotation.BundleController;

/**
 * @author wuxii@foxmail.com
 */
@BundleController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientInfoService clientInfoService;

    @PostMapping("/save")
    public ClientInfo save(ClientInfo clientInfo) {
        return clientInfoService.save(clientInfo);
    }

    @GetMapping("/default")
    public ClientInfo insertDefault() {
        return clientInfoService.insertDefault();
    }

    @PostMapping("/register")
    public ClientInfo register(ClientInfo clientInfo) {
        return null;
    }

}
