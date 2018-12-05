package com.harmony.kindless.wechat.autoconfig;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInRedisConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuxii
 */
@Configuration
@ConditionalOnClass(WxMpService.class)
@EnableConfigurationProperties(WeixinMpProperties.class)
public class WeixinMpAutoConfiguration {

    private final WeixinMpProperties weixinMpProperties;

    public WeixinMpAutoConfiguration(WeixinMpProperties weixinMpProperties) {
        this.weixinMpProperties = weixinMpProperties;
    }


    @Bean
    public WxMpService wxMpService(WxMpConfigStorage wxMpConfigStorage) {
        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInRedisConfigStorage wxMpInRedisConfigStorage = new WxMpInRedisConfigStorage(null);
        return wxMpInRedisConfigStorage;
    }


}
