package com.harmony.kindless.wechat.service;

import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;

/**
 * @author wuxii
 */
public interface WeixinService {

    WxMpService getDefaultWxMpService();

    WxMpService getWxMpService(String appId);

    WxMpMessageRouter getDefaultWxMessageRouter();

    WxMpMessageRouter getWxMpMessageRouter(String appId);

    /**
     * 将微信相关的配置保存到数据库中
     *
     * @param appId
     */
    void store(String appId);

    void storeAll();

    void clearAll();
    
}
