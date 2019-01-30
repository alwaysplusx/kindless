package com.harmony.kindless.core.controller;

import com.harmony.kindless.core.service.WeixinService;
import com.harmony.umbrella.web.method.annotation.BundleView;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuxii
 */
@Controller
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

        log.info("接收来自微信的消息: \n{}", payload);
        try {
            boolean isEncryptedMessage = "aes".equals(encType);
            WxMpXmlMessage inMessage = isEncryptedMessage
                    ? WxMpXmlMessage.fromEncryptedXml(payload, wxMpService.getWxMpConfigStorage(), timestamp, nonce, msgSignature)
                    : WxMpXmlMessage.fromXml(payload);
            WxMpXmlOutMessage outMessage = weixinService.routeMessage(inMessage);
            return outMessage == null ? "" : outMessage.toXml();
        } catch (Exception e) {
            log.info("处理微信消息失败.", e);
            return "error";
        }
    }

    @BundleView
    @GetMapping("/users")
    public WxMpUser users(String openid) throws WxErrorException {
        WxMpUserService userService = weixinService.getWxService(WxMpUserService.class);
        return userService.userInfo(openid);
    }

    @ResponseBody
    @GetMapping("/oauth")
    public WxMpOAuth2AccessToken oauth(String code, String state) throws WxErrorException {
        WxMpService wxMpService = weixinService.getWxMpService();
        WxMpOAuth2AccessToken result = wxMpService.oauth2getAccessToken(code);
        String openId = result.getOpenId();
        log.info("bind user {}", openId);
        return result;
    }

}
