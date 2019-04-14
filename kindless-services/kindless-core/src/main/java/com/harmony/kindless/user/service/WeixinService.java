package com.harmony.kindless.user.service;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author wuxii
 */
public interface WeixinService {

    WxMpService getWxMpService();

    <T> T getWxService(Class<T> serviceType);

    WxMpXmlOutMessage routeMessage(WxMpXmlMessage inMessage);

}
