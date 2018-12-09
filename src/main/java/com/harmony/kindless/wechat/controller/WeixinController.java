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

    @ResponseBody
    @RequestMapping("/touch")
    public String auth(@RequestBody(required = false) String payload,
                       @RequestParam(name = "signature", required = false) String signature,
                       @RequestParam(name = "timestamp", required = false) String timestamp,
                       @RequestParam(name = "nonce", required = false) String nonce,
                       @RequestParam(name = "echostr", required = false) String echostr,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {

        log.info("接收到微信请求: [{}, {}, {}]", signature, timestamp, nonce);

        WxMpService wxMpService = weixinService.getDefaultWxMpService();
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

        WxMpConfigStorage wxMpConfig = wxMpService.getWxMpConfigStorage();
        boolean isEncryptedMessage = "aes".equals(encType);
        WxMpXmlMessage inMessage = isEncryptedMessage
                ? WxMpXmlMessage.fromEncryptedXml(payload, wxMpConfig, timestamp, nonce, msgSignature)
                : WxMpXmlMessage.fromXml(payload);

        log.info("处理来自微信的事件消息: \t\n{}\t\n->{}", payload, inMessage);
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
