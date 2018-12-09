package com.harmony.kindless.wechat.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.harmony.kindless.apis.domain.weixin.WeixinMpConfig;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wuxii
 */
public class WxMpInRedisTemplateConfigStorage implements WxMpConfigStorage {

    private static final String ACCESS_TOKEN = "accessToken";
    private static final String EXPIRES_TIME = "expiresTime";

    private static final String JSAPI_TICKET = "jsapiTicket";
    private static final String JSAPI_TICKET_EXPIRES_TIME = "jsapiTicketExpiresTime";

    private static final String CARDAPI_TICKET = "cardApiTicket";
    private static final String CARDAPI_TICKET_EXPIRES_TIME = "cardApiTicketExpiresTime";

    private static final List<String> STRONG_KEYS = Arrays.asList(
            "appId",
            "secret",
            "token",
            "redirectUri",
            "aesKey",
            "templateId",
            "proxyPort",
            "proxyHost",
            "proxyUsername",
            "proxyPassword",
            "allowAutoRefreshToken"
    );

    private static final List<String> WEAK_KEYS = Arrays.asList(
            "accessToken",
            "expiresTime",
            "jsapiTicket",
            "jsapiTicketExpiresTime",
            "cardApiTicket",
            "cardApiTicketExpiresTime"
    );

    private RedisTemplate<String, String> redisTemplate;

    private final String prefix;

    // 只能通过init进行初始化
    private String appId;
    private String redisKey;

    private final Lock accessTokenLock = new ReentrantLock();
    private final Lock jsapiTicketLock = new ReentrantLock();
    private final Lock cardApiTicketLock = new ReentrantLock();

    public WxMpInRedisTemplateConfigStorage(RedisTemplate<String, String> redisTemplate) {
        this("weixin:mp", redisTemplate);
    }

    public WxMpInRedisTemplateConfigStorage(String prefix, RedisTemplate<String, String> redisTemplate) {
        this.prefix = prefix;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 初始化WxMpRedisTemplateConfigStorage, 将把值都写入到redis中
     *
     * @param weixinMpConfig
     */
    public synchronized void initWithWeixinMpConfig(WeixinMpConfig weixinMpConfig) {
        initWithWeixinMpConfig(weixinMpConfig, false);
    }

    public synchronized void initWithWeixinMpConfig(WeixinMpConfig weixinMpConfig, boolean force) {
        this.appId = weixinMpConfig.getAppId();
        this.redisKey = prefix + ":" + appId;
        // set all value into redis
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        JSONObject values = (JSONObject) JSON.toJSON(weixinMpConfig);
        for (String key : values.keySet()) {
            String value = values.getString(key);
            if (value != null) {
                if (STRONG_KEYS.contains(key)) {
                    hashOperations.put(redisKey, key, value);
                } else if (WEAK_KEYS.contains(key)
                        && (force || !hashOperations.hasKey(redisKey, key))) {
                    hashOperations.put(redisKey, key, value);
                }
            }
        }
    }

    public synchronized void clear() {
        this.redisTemplate.delete(redisKey);
        this.appId = null;
        this.redisKey = null;
    }

    // 写操作

    @Override
    public void expireAccessToken() {
        setValueToRedis(EXPIRES_TIME, 0);
    }

    @Override
    public void updateAccessToken(WxAccessToken accessToken) {
        this.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
    }

    @Override
    public void updateAccessToken(String accessToken, int expiresInSeconds) {
        setValueToRedis(ACCESS_TOKEN, accessToken);
        setValueToRedis(EXPIRES_TIME, currentExpiresTime(expiresInSeconds));
    }

    @Override
    public void expireJsapiTicket() {
        setValueToRedis(JSAPI_TICKET_EXPIRES_TIME, 0);
    }

    @Override
    public void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
        setValueToRedis(JSAPI_TICKET, jsapiTicket);
        setValueToRedis(JSAPI_TICKET_EXPIRES_TIME, currentExpiresTime(expiresInSeconds));
    }

    @Override
    public void expireCardApiTicket() {
        setValueToRedis(CARDAPI_TICKET_EXPIRES_TIME, 0);
    }

    @Override
    public void updateCardApiTicket(String cardApiTicket, int expiresInSeconds) {
        setValueToRedis(CARDAPI_TICKET, cardApiTicket);
        setValueToRedis(CARDAPI_TICKET_EXPIRES_TIME, currentExpiresTime(expiresInSeconds));
    }

    // 读操作

    @Override
    public String getAccessToken() {
        return getValueFromRedis("accessToken");
    }

    @Override
    public Lock getAccessTokenLock() {
        return accessTokenLock;
    }

    @Override
    public boolean isAccessTokenExpired() {
        return System.currentTimeMillis() > getExpiresTime();
    }

    @Override
    public String getJsapiTicket() {
        return getValueFromRedis(JSAPI_TICKET);
    }

    @Override
    public Lock getJsapiTicketLock() {
        return jsapiTicketLock;
    }

    public long getJsapiTicketExpiresTime() {
        return getValueFromRedis(JSAPI_TICKET_EXPIRES_TIME, Long.TYPE);
    }

    @Override
    public boolean isJsapiTicketExpired() {
        return System.currentTimeMillis() > getJsapiTicketExpiresTime();
    }

    @Override
    public String getCardApiTicket() {
        return getValueFromRedis(CARDAPI_TICKET);
    }

    @Override
    public Lock getCardApiTicketLock() {
        return cardApiTicketLock;
    }

    public long getCardExpiresTime() {
        return getValueFromRedis(CARDAPI_TICKET_EXPIRES_TIME, Long.TYPE);
    }

    @Override
    public boolean isCardApiTicketExpired() {
        return System.currentTimeMillis() > getCardExpiresTime();
    }

    @Override
    public long getExpiresTime() {
        return getValueFromRedis(EXPIRES_TIME, Long.TYPE);
    }

    @Override
    public File getTmpDirFile() {
        return null;
    }

    @Override
    public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
        return null;
    }

    @Override
    public String getAppId() {
        return getValueFromRedis("appId");
    }

    @Override
    public String getSecret() {
        return getValueFromRedis("secret");
    }

    @Override
    public String getToken() {
        return getValueFromRedis("token");
    }

    @Override
    public String getAesKey() {
        return getValueFromRedis("aesKey");
    }

    @Override
    public String getTemplateId() {
        return getValueFromRedis("templateId");
    }

    @Override
    public String getOauth2redirectUri() {
        return getValueFromRedis("redirectUri");
    }

    @Override
    public String getHttpProxyHost() {
        return getValueFromRedis("proxyHost");
    }

    @Override
    public int getHttpProxyPort() {
        return getValueFromRedis("proxyPort", Integer.TYPE);
    }

    @Override
    public String getHttpProxyUsername() {
        return getValueFromRedis("proxyUsername");
    }

    @Override
    public String getHttpProxyPassword() {
        return getValueFromRedis("proxyPassword");
    }

    @Override
    public boolean autoRefreshToken() {
        return getValueFromRedis("allowAutoRefreshToken", Boolean.TYPE);
    }

    private long currentExpiresTime(int expiresInSeconds) {
        // 预留120秒缓冲(可配置)
        return System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresInSeconds - 120);
    }

    private String getValueFromRedis(String key) {
        return getValueFromRedis(key, String.class);
    }

    private <T> T getValueFromRedis(String key, Class<T> resultType) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String value = hashOperations.get(redisKey, key);
        if (String.class.equals(resultType)) {
            return (T) value;
        } else if (resultType == Boolean.TYPE) {
            return (T) Boolean.valueOf(value);
        } else if (resultType == Long.TYPE) {
            return (T) Long.valueOf(value == null ? 0l : Long.parseLong(value));
        } else if (resultType == Integer.TYPE) {
            return (T) Integer.valueOf(value == null ? 0 : Integer.parseInt(value));
        }
        return (T) value;
    }

    private void setValueToRedis(String key, Object value) {
        if (value != null) {
            redisTemplate.opsForHash().put(redisKey, key, value.toString());
        }
    }

}
