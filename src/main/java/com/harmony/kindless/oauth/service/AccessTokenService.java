package com.harmony.kindless.oauth.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;

import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.kindless.shiro.JwtToken.OriginClaims;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii@foxmail.com
 */
public interface AccessTokenService extends Service<AccessToken, String> {

    /**
     * 根据提供的scope code来授予access token
     * 
     * @param sc
     *            scope token
     * @return access token
     * @throws OAuthProblemException
     */
    AccessToken grant(ScopeCode sc) throws OAuthProblemException;

    /**
     * 根据提供的scope code来创建指定失效时间的access token
     * 
     * @param sc
     *            scope code
     * @param grantProperties
     *            授权的配置项
     * @return access token
     * @throws OAuthProblemException
     */
    AccessToken grant(ScopeCode sc, GrantProperties grantProperties) throws OAuthProblemException;

    /**
     * 根据提供的client info来授予access token
     * 
     * @param ci
     *            client info
     * @return access token
     * @throws OAuthProblemException
     */
    AccessToken grant(ClientInfo ci) throws OAuthProblemException;

    /**
     * 根据提供的client info来创建access token
     * 
     * @param ci
     *            client info
     * @param grantProperties
     *            授权配置项
     * @return access token
     * @throws OAuthProblemException
     */
    AccessToken grant(ClientInfo ci, GrantProperties grantProperties) throws OAuthProblemException;

    /**
     * 授权access token的一些配置属性
     * 
     * @author wuxii@foxmail.com
     */
    public static class GrantProperties {

        /**
         * 授权的token有效时长
         */
        private int expiresSeconds;

        /**
         * refresh token失效时长
         */
        private int refreshTokenExpiresSeconds;
        /**
         * token的作用origin
         */
        private OriginClaims originClaims;

        public GrantProperties(int expiresSeconds, HttpServletRequest request) {
            this(expiresSeconds, new OriginClaims(request));
        }

        public GrantProperties(int expiresSeconds, OriginClaims originClaims) {
            this.expiresSeconds = expiresSeconds;
            this.originClaims = originClaims;
        }

        public GrantProperties(int expiresSeconds, int refreshTokenExpiredSeconds) {
            this.expiresSeconds = expiresSeconds;
            this.refreshTokenExpiresSeconds = refreshTokenExpiredSeconds;
        }

        public int getExpiresSeconds() {
            return expiresSeconds;
        }

        public void setExpiresSeconds(int expiresSeconds) {
            this.expiresSeconds = expiresSeconds;
        }

        public int getRefreshTokenExpiresSeconds() {
            return refreshTokenExpiresSeconds;
        }

        public void setRefreshTokenExpiresSeconds(int refreshTokenExpiresSeconds) {
            this.refreshTokenExpiresSeconds = refreshTokenExpiresSeconds;
        }

        public OriginClaims getOriginClaims() {
            return originClaims;
        }

        public void setOriginClaims(OriginClaims originClaims) {
            this.originClaims = originClaims;
        }

    }

}
