package com.harmony.kindless.oauth.service;

import java.util.Set;

import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.oauth.domain.ClientInfo;
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii@foxmail.com
 */
public interface ScopeCodeService extends Service<ScopeCode, String> {

    /**
     * 用户在第三方的引导下进行scope code授权, 第三方得到scope code后可升级为access token
     * 
     * @param user
     *            系统用户
     * @param client
     *            第三方
     * @param scopes
     *            用户所允许的授权范围
     * @return scope code
     */
    ScopeCode grant(User user, ClientInfo client, Set<String> scopes);

}
