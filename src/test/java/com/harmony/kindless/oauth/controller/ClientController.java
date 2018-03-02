package com.harmony.kindless.oauth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.service.ClientInfoService;
import com.harmony.umbrella.data.query.QueryBundle;
import com.harmony.umbrella.data.query.QueryFeature;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleQuery;

/**
 * @author wuxii@foxmail.com
 */
@BundleController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientInfoService clientInfoService;

    @PostMapping({ "/save", "/create", "/register" })
    public ClientInfo create(@RequestBody ClientInfo clientInfo) {
        return clientInfoService.register(clientInfo);
    }

    @GetMapping("/page")
    public Page<ClientInfo> page(QueryBundle<ClientInfo> bundle) {
        return clientInfoService.findPage(bundle);
    }

    @GetMapping("/list")
    @BundleQuery(feature = { QueryFeature.FULL_TABLE_QUERY })
    public List<ClientInfo> list(QueryBundle<ClientInfo> bundle) {
        return clientInfoService.findList(bundle);
    }

    @GetMapping("/delete/{username}")
    public void delete(@PathVariable("clientId") String clientId) {
        clientInfoService.delete(clientId);
    }

    @GetMapping("/view/{clientId}")
    public ClientInfo view(@PathVariable("clientId") String clientInfo) {
        return clientInfoService.findOne(clientInfo);
    }
}