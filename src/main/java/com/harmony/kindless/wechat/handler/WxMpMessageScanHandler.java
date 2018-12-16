package com.harmony.kindless.wechat.handler;

import com.harmony.kindless.wechat.WxMpMessageRuleHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wuxii
 */
@Component
public class WxMpMessageScanHandler implements WxMpMessageRuleHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        return null;
    }

    @Override
    public void config(WxMpMessageRouter router) {
        router.rule()
                .handler(this)
                .event(WxConsts.EventType.SCAN)
                .end();
    }

}
