package com.harmony.kindless.wechat.controller;

import com.harmony.kindless.wechat.service.WeixinService;
import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleView;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
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

    @GetMapping("/auth")
    public String auth(@RequestParam(name = "signature", required = false) String signature,
                       @RequestParam(name = "timestamp", required = false) String timestamp,
                       @RequestParam(name = "nonce", required = false) String nonce,
                       @RequestParam(name = "echostr", required = false) String echostr) {
        log.info("接收到来自微信的接入确认请求: [{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
        WxMpService wxMpService = weixinService.getDefaultWxMpService();
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)
                || !wxMpService.checkSignature(timestamp, nonce, signature)) {
            return "illegal request";
        }
        return echostr;
    }

    @PostMapping("/touch")
    public String touch(@RequestBody String payload,
                        @RequestParam("signature") String signature,
                        @RequestParam(name = "encrypt_type", required = false) String encType,
                        @RequestParam(name = "msg_signature", required = false) String msgSignature,
                        @RequestParam("timestamp") String timestamp,
                        @RequestParam("nonce") String nonce) {

        WxMpService wxMpService = weixinService.getDefaultWxMpService();
        if (StringUtils.isAnyBlank(signature, timestamp, nonce)
                || wxMpService.checkSignature(timestamp, nonce, signature)) {
            log.info("来自微信touch的非法请求: [{}, {}, {}]", timestamp, nonce, signature);
            return "illegal request";
        }

        WxMpConfigStorage wxMpConfig = wxMpService.getWxMpConfigStorage();
        boolean isEncryptedMessage = "aes".equals(encType);
        WxMpXmlMessage inMessage = isEncryptedMessage
                ? WxMpXmlMessage.fromEncryptedXml(payload, wxMpConfig, timestamp, nonce, msgSignature)
                : WxMpXmlMessage.fromXml(payload);

        log.info("接收来自微信的请求: {}", inMessage);
        WxMpXmlOutMessage outMessage = weixinService.getDefaultWxMessageRouter().route(inMessage);
        return isEncryptedMessage
                ? outMessage.toEncryptedXml(wxMpConfig)
                : outMessage.toXml();
    }

    @BundleView
    @GetMapping("/users")
    public Object users(String openid) throws WxErrorException {
        WxMpService wxMpService = weixinService.getDefaultWxMpService();
        WxMpUserService userService = wxMpService.getUserService();
        return userService.userInfo(openid);
    }

    @BundleView
    @PostMapping("/store")
    public Response storeAll() {
        weixinService.storeAll();
        return Response.ok();
    }

    @PostMapping("/clear")
    public Response clear() {
        weixinService.clearAll();
        return Response.ok();
    }

}
