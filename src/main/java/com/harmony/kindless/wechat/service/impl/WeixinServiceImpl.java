package com.harmony.kindless.wechat.service.impl;

import com.harmony.kindless.apis.domain.weixin.WeixinMpConfig;
import com.harmony.kindless.wechat.repository.WeixinMpConfigRepository;
import com.harmony.kindless.wechat.service.WeixinService;
import com.harmony.kindless.wechat.support.WxMpInRedisTemplateConfigStorage;
import com.harmony.umbrella.data.JpaQueryBuilder;
import com.harmony.umbrella.data.QueryBundle;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuxii
 */
@Service
public class WeixinServiceImpl implements WeixinService {

    private final Map<String, WxMpService> wxMpServices = new ConcurrentHashMap<>();

    @Value("${weixin.mp.default.appId:wxee4315284079d53b}")
    private String defaultAppId;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private WeixinMpConfigRepository weixinMpConfigRepository;

    @Override
    public WxMpService getDefaultWxMpService() {
        return getWxMpService(defaultAppId);
    }

    @Override
    public WxMpService getWxMpService(String appId) {
        WxMpService wxMpService = wxMpServices.get(appId);
        if (wxMpService == null) {
            synchronized (wxMpServices) {
                wxMpService = buildWxMpService(appId);
                wxMpServices.put(appId, wxMpService);
            }
        }
        return wxMpService;
    }

    protected WxMpService buildWxMpService(String appId) {
        QueryBundle<WeixinMpConfig> bundle = JpaQueryBuilder.newBuilder(WeixinMpConfig.class)
                .equal("appId", appId)
                .bundle();
        WxMpConfigStorage wxMpConfigStorage = weixinMpConfigRepository
                .getSingleResult(bundle)
                .map(this::buildWxMpConfigStorage)
                .orElseThrow(RuntimeException::new);
        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
        return wxMpService;
    }

    private WxMpConfigStorage buildWxMpConfigStorage(WeixinMpConfig cfg) {
        WxMpInRedisTemplateConfigStorage storage = new WxMpInRedisTemplateConfigStorage(redisTemplate);
        storage.initWithWeixinMpConfig(cfg);
        return storage;
    }

}
