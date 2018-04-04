package com.harmony.kindless.jwt;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * jwt 分多种的使用情况
 * <ul>
 * <li>仅用户
 * <li>仅第三方(客户端)
 * <li>用户授权的第三方(第三方以用户的名义发起的请求)
 * </ul>
 * 
 * @author wuxii@foxmail.com
 */
public class JwtToken implements Serializable {

    private static final long serialVersionUID = 7631811951212850309L;

    private final String token;

    private RequestOriginProperties requestOriginProperties;

    private String username;
    private String clientId;
    private DecodedJWT jwt;

    public JwtToken(String token) {
        this(token, null);
    }

    public JwtToken(String token, HttpServletRequest request) {
        this.token = token;
        this.setHttpRequest(request);
        this.jwt = JWT.decode(token);
        if (jwt.getAudience() != null && !jwt.getAudience().isEmpty()) {
            this.username = jwt.getAudience().get(0);
        }
        this.clientId = getStringClaim("client");
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getClientId() {
        return clientId;
    }

    public RequestOriginProperties getRequestOriginProperties() {
        return requestOriginProperties;
    }

    /**
     * token是否只供用户单独使用(用户采用账号密码登录后得到的jwt)
     * 
     * @return true is only for user
     */
    public boolean isUserOnly() {
        return clientId == null && username != null;
    }

    /**
     * token是否只供第三方客户端使用(客户端向系统申请的jwt)
     * 
     * @return
     */
    public boolean isThirdPartOnly() {
        return username == null;
    }

    /**
     * token是否是用户代理授权的第三方代理
     * 
     * @return
     */
    public boolean isUserProxy() {
        return clientId == null;
    }

    public boolean isExpired() {
        Date expiresAt = jwt.getExpiresAt();
        return expiresAt != null && System.currentTimeMillis() > expiresAt.getTime();
    }

    public String getStringClaim(String name) {
        Claim claim = jwt.getClaim(name);
        return claim != null ? claim.asString() : null;
    }

    public DecodedJWT getJwtToken() {
        return jwt;
    }

    protected void setHttpRequest(HttpServletRequest request) {
        if (request != null) {
            this.requestOriginProperties = new RequestOriginProperties(request);
        }
    }

}