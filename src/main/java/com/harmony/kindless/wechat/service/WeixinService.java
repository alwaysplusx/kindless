package com.harmony.kindless.wechat.service;

import me.chanjar.weixin.mp.api.WxMpService;

/**
 * @author wuxii
 */
public interface WeixinService {

    WxMpService getDefaultWxMpService();

    WxMpService getWxMpService(String appId);

}
