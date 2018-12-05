package com.harmony.kindless.wechat.service.impl;

import com.harmony.kindless.wechat.repository.WeixinMpConfigRepository;
import com.harmony.kindless.wechat.service.WeixinService;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxii
 */
@Service
public class WeixinServiceImpl implements WeixinService {

    @Autowired
    private WeixinMpConfigRepository weixinMpConfigRepository;

    @Override
    public WxMpConfigStorage getDefaultWxMpConfigStorage() {
        return null;
    }

    @Override
    public WxMpConfigStorage getWxMpConfigStorage(String appId) {
        return null;
    }

}
