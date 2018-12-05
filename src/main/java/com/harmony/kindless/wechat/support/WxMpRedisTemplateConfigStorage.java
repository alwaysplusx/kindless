package com.harmony.kindless.wechat.support;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.File;
import java.util.concurrent.locks.Lock;

/**
 * @author wuxii
 */
public class WxMpRedisTemplateConfigStorage implements WxMpConfigStorage {

    private String appId;
    private String secret;

    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String getAccessToken() {
        return null;
    }

    @Override
    public Lock getAccessTokenLock() {
        return null;
    }

    @Override
    public boolean isAccessTokenExpired() {
        return false;
    }

    @Override
    public void expireAccessToken() {
    }

    @Override
    public void updateAccessToken(WxAccessToken accessToken) {
    }

    @Override
    public void updateAccessToken(String accessToken, int expiresInSeconds) {
    }

    @Override
    public String getJsapiTicket() {
        return null;
    }

    @Override
    public Lock getJsapiTicketLock() {
        return null;
    }

    @Override
    public boolean isJsapiTicketExpired() {
        return false;
    }

    @Override
    public void expireJsapiTicket() {

    }

    @Override
    public void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {

    }

    @Override
    public String getCardApiTicket() {
        return null;
    }

    @Override
    public Lock getCardApiTicketLock() {
        return null;
    }

    @Override
    public boolean isCardApiTicketExpired() {
        return false;
    }

    @Override
    public void expireCardApiTicket() {

    }

    @Override
    public void updateCardApiTicket(String cardApiTicket, int expiresInSeconds) {

    }

    @Override
    public String getAppId() {
        return null;
    }

    @Override
    public String getSecret() {
        return null;
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public String getAesKey() {
        return null;
    }

    @Override
    public String getTemplateId() {
        return null;
    }

    @Override
    public long getExpiresTime() {
        return 0;
    }

    @Override
    public String getOauth2redirectUri() {
        return null;
    }

    @Override
    public String getHttpProxyHost() {
        return null;
    }

    @Override
    public int getHttpProxyPort() {
        return 0;
    }

    @Override
    public String getHttpProxyUsername() {
        return null;
    }

    @Override
    public String getHttpProxyPassword() {
        return null;
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
    public boolean autoRefreshToken() {
        return false;
    }

}
