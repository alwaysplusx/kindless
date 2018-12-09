package com.harmony.kindless.wechat.service.impl;

import com.harmony.kindless.apis.domain.weixin.WeixinMpConfig;
import com.harmony.kindless.wechat.handler.WxMpMessageLogHandler;
import com.harmony.kindless.wechat.handler.WxMpMessageSubscribeHandler;
import com.harmony.kindless.wechat.repository.WeixinMpConfigRepository;
import com.harmony.kindless.wechat.service.WeixinService;
import com.harmony.kindless.wechat.support.WxMpInRedisTemplateConfigStorage;
import com.harmony.umbrella.data.JpaQueryBuilder;
import com.harmony.umbrella.data.QueryBundle;
import com.harmony.umbrella.data.Selections;
import com.harmony.umbrella.data.result.CellResult;
import com.harmony.umbrella.data.result.RowResult;
import com.harmony.umbrella.util.TimeUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuxii
 */
@Service
public class WeixinServiceImpl implements WeixinService {

    private static final Logger log = LoggerFactory.getLogger(WeixinServiceImpl.class);

    private final Map<String, WxMpService> wxMpServices = new ConcurrentHashMap<>();

    private final Map<String, WxMpMessageRouter> wxMpMessageRouters = new ConcurrentHashMap<>();

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
                log.info("创建[{}]微信服务", appId);
                wxMpService = buildWxMpService(appId);
                wxMpServices.put(appId, wxMpService);
            }
        }
        return wxMpService;
    }

    @Override
    public WxMpMessageRouter getDefaultWxMessageRouter() {
        return getWxMpMessageRouter(defaultAppId);
    }

    @Override
    public WxMpMessageRouter getWxMpMessageRouter(String appId) {
        WxMpMessageRouter router = wxMpMessageRouters.get(appId);
        if (router == null) {
            synchronized (wxMpMessageRouters) {
                router = buildWxMpRouter(appId);
                wxMpMessageRouters.put(appId, router);
            }
        }
        return router;
    }

    @Override
    public void store(String appId) {
        log.info("将[{}]相关的微信配置存入数据库中", appId);
        wxMpServices
                .values()
                .stream()
                .map(WxMpService::getWxMpConfigStorage)
                .filter(appId::equals)
                .map(this::extractWeixinMpConfig)
                .forEach(this::storeByAppId);
    }

    @Override
    public void storeAll() {
        Set<String> appIds = wxMpServices.keySet();
        if (appIds.isEmpty()) {
            log.info("未找到微信配置, 无需存入数据");
            return;
        }
        log.info("将所有微信相关配置存入数据库中. {}", appIds);
        wxMpServices
                .values()
                .stream()
                .map(WxMpService::getWxMpConfigStorage)
                .map(this::extractWeixinMpConfig)
                .forEach(this::storeByAppId);
    }

    @Override
    public void clearAll() {
        wxMpServices
                .values()
                .stream()
                .map(WxMpService::getWxMpConfigStorage)
                .forEach(e -> {
                    if (e instanceof WxMpInRedisTemplateConfigStorage) {
                        ((WxMpInRedisTemplateConfigStorage) e).clear();
                    }
                });
    }

    protected WxMpMessageRouter buildWxMpRouter(String appId) {
        WxMpService wxMpService = getWxMpService(appId);
        // @formatter:off
        return new WxMpMessageRouter(wxMpService)
                        .rule()
                            .handler(new WxMpMessageLogHandler())
                        .next()
                        .rule()
                            .event(WxConsts.EventType.SUBSCRIBE)
                            .handler(new WxMpMessageSubscribeHandler())
                        .next();
        // @formatter:on
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

    @PreDestroy
    private void destroy() {
        storeAll();
    }

    private WeixinMpConfig extractWeixinMpConfig(WxMpConfigStorage cfg) {
        WeixinMpConfig.WeixinMpConfigBuilder builder = WeixinMpConfig
                .builder()
                .appId(cfg.getAppId())
                .secret(cfg.getSecret())
                .token(cfg.getToken())
                .redirectUri(cfg.getOauth2redirectUri())
                .aesKey(cfg.getAesKey())
                .templateId(cfg.getTemplateId())
                .proxyPort(cfg.getHttpProxyPort())
                .proxyHost(cfg.getHttpProxyHost())
                .proxyUsername(cfg.getHttpProxyUsername())
                .proxyPassword(cfg.getHttpProxyPassword())
                .allowAutoRefreshToken(cfg.autoRefreshToken())
                .accessToken(cfg.getAccessToken())
                .expiresTime(TimeUtils.toDate(cfg.getExpiresTime()))
                .jsapiTicket(cfg.getJsapiTicket())
                .cardApiTicket(cfg.getCardApiTicket());
        if (cfg instanceof WxMpInRedisTemplateConfigStorage) {
            long jsapiTicketExpiresTime = ((WxMpInRedisTemplateConfigStorage) cfg).getJsapiTicketExpiresTime();
            long cardExpiresTime = ((WxMpInRedisTemplateConfigStorage) cfg).getCardExpiresTime();
            builder.jsapiTicketExpiresTime(TimeUtils.toDate(jsapiTicketExpiresTime))
                    .cardApiTicketExpiresTime(TimeUtils.toDate(cardExpiresTime));
        }
        return builder.build();
    }

    private void storeByAppId(WeixinMpConfig cfg) {

        QueryBundle<WeixinMpConfig> bundle = JpaQueryBuilder
                .newBuilder(WeixinMpConfig.class)
                .equal("appId", cfg.getAppId())
                .bundle();

        weixinMpConfigRepository
                .query(bundle)
                .getSingleResult(Selections.of("id"))
                .map(RowResult::firstCellResult)
                .map(CellResult::getValue)
                .map(Object::toString)
                .map(Long::valueOf)
                .ifPresent(cfg::setId);

        weixinMpConfigRepository.save(cfg);
    }

}
