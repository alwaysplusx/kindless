package com.harmony.kindless.wechat.handler;

import com.alibaba.fastjson.JSON;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author wuxii
 */
public class WxMpMessageLogHandler implements WxMpMessageHandler {

    private static final Logger log = LoggerFactory.getLogger(WxMpMessageLogHandler.class);

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        try {
            log.info("接收微信消息: {}", JSON.toJSONString(wxMessage));
        } catch (Exception e) {
            log.error("记录微信消息异常", e);
        }
        return null;
    }

}
