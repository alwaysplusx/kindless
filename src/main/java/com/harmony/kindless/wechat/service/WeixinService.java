package com.harmony.kindless.wechat.service;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;

/**
 * @author wuxii
 */
public interface WeixinService {

    /**
     * 获取系统默认的微信公众号配置
     *
     * @return
     */
    WxMpConfigStorage getDefaultWxMpConfigStorage();

    /**
     * 根据appId获取微信的配置
     *
     * @param appId appId
     * @return
     */
    WxMpConfigStorage getWxMpConfigStorage(String appId);

}
