package com.harmony.kindless.weixin.handler;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wuxii
 */
@Component
public class WxMpMessageSubscribeHandler implements WxMpMessageRuleHandler {

    private static final Logger log = LoggerFactory.getLogger(WxMpMessageSubscribeHandler.class);

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        String fromUser = wxMessage.getFromUser();
        log.info("新的关注用户: {}", fromUser);

        WxMpUserService userService = wxMpService.getUserService();
        WxMpUser wxMpUser = userService.userInfo(fromUser);
        log.info("新关注用户的详细信息: {}", wxMpUser);

        return WxMpXmlOutMessage
                .TEXT()
                .content("感谢您的关注")
                .fromUser(fromUser)
                .toUser(fromUser)
                .build();
    }

    @Override
    public void config(WxMpMessageRouter router) {
        router.rule()
                .event(WxConsts.EventType.SUBSCRIBE)
                .handler(this)
                .end();
    }
}
