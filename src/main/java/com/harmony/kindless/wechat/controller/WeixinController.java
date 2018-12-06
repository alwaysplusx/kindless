package com.harmony.kindless.wechat.controller;

import com.harmony.kindless.wechat.service.WeixinService;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleView;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wuxii
 */
@BundleController
@RequestMapping("/weixin")
public class WeixinController {

    @Autowired
    private WeixinService weixinService;

    @BundleView
    @GetMapping("/users")
    public Object users(String openid) throws WxErrorException {
        WxMpService wxMpService = weixinService.getDefaultWxMpService();
        WxMpUserService userService = wxMpService.getUserService();
        return userService.userInfo(openid);
    }

}
