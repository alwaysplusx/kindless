package com.kindless.user.weixin.handler;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;

/**
 * @author wuxii
 */
public interface WxMpMessageRuleHandler extends WxMpMessageHandler {

    void config(WxMpMessageRouter router);

}
