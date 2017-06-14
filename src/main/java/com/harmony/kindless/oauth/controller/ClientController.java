package com.harmony.kindless.oauth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.service.ClientInfoService;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.web.method.annotation.BundleController;

/**
 * @author wuxii@foxmail.com
 */
@BundleController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientInfoService clientInfoService;

    @PostMapping({ "/save", "/add", "/register" })
    public ClientInfo save(ClientInfo clientInfo) {
        return clientInfoService.save(clientInfo);
    }

    @GetMapping("/page")
    public Page<ClientInfo> page(QueryBundle<ClientInfo> bundle) {
        return null;
    }

    @GetMapping("/list")
    public List<ClientInfo> list() {
        return null;
    }

    @GetMapping("/delete/{username}")
    public void delete(@PathVariable("clientId") String username) {
    }

    @GetMapping("/view/{clientId}")
    public ClientInfo view(@PathVariable("clientId") String clientInfo) {
        return null;
    }
}
