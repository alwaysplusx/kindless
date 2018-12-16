package com.harmony.kindless.wechat.controller;

import com.harmony.kindless.wechat.service.WeixinService;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleView;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuxii
 */
@BundleController
@RequestMapping("/weixin")
public class WeixinController {

    private static final Logger log = LoggerFactory.getLogger(WeixinController.class);

    @Autowired
    private WeixinService weixinService;

    @ResponseBody
    @RequestMapping("/touch")
    public String touch(@RequestBody(required = false) String payload,
                        @RequestParam(name = "signature", required = false) String signature,
                        @RequestParam(name = "timestamp", required = false) String timestamp,
                        @RequestParam(name = "nonce", required = false) String nonce,
                        @RequestParam(name = "echostr", required = false) String echostr,
                        @RequestParam(name = "encrypt_type", required = false) String encType,
                        @RequestParam(name = "msg_signature", required = false) String msgSignature) {

        log.info("接收到微信请求: [{}, {}, {}]", signature, timestamp, nonce);
        WxMpService wxMpService = weixinService.getWxMpService();

        if (StringUtils.isAnyBlank(signature, timestamp, nonce)
                || !wxMpService.checkSignature(timestamp, nonce, signature)) {
            log.info("非法的微信请求, 签名验证不通过");
            return "illegal request";
        }

        if (StringUtils.isNotBlank(echostr)) {
            log.info("微信接入请求, 验证通过. {}", echostr);
            return echostr;
        }

        if (StringUtils.isBlank(payload)) {
            return "illegal request";
        }

        try {
            boolean isEncryptedMessage = "aes".equals(encType);
            WxMpXmlMessage inMessage = isEncryptedMessage
                    ? WxMpXmlMessage.fromEncryptedXml(payload, wxMpService.getWxMpConfigStorage(), timestamp, nonce, msgSignature)
                    : WxMpXmlMessage.fromXml(payload);

            WxMpXmlOutMessage outMessage = weixinService.routeMessage(inMessage);
            log.info("处理来自微信的事件消息: \n{}", inMessage);
            return outMessage == null ? "" : outMessage.toXml();
        } catch (Exception e) {
            log.info("处理微信消息失败.", e);
            return "error";
        }
    }

    @BundleView
    @GetMapping("/users")
    public Object users(String openid) throws WxErrorException {
        WxMpUserService userService = weixinService.getWxService(WxMpUserService.class);
        return userService.userInfo(openid);
    }

}
