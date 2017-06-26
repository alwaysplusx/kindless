package com.harmony.kindless.oauth.service;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.kindless.oauth.repository.ScopeCodeRepository;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class ScopeCodeService extends ServiceSupport<ScopeCode, String> {

    @Autowired
    private ScopeCodeRepository scopeCodeRepository;

    @Override
    protected QueryableRepository<ScopeCode, String> getRepository() {
        return scopeCodeRepository;
    }

    /**
     * 用户给客户端授予临时编码
     * 
     * @param username
     *            用户名
     * @param clientId
     *            客户端id
     * @param scope
     *            授予的权限
     * @return 临时编码
     */
    public ScopeCode grant(String username, String clientId, Set<String> scopes) {
        // FIXME code 生成格式定制
        ScopeCode scopeCode = new ScopeCode();
        scopeCode.setClientId(clientId);
        scopeCode.setCreatedTime(new Date());
        scopeCode.setExpiresIn(7200);
        scopeCode.setRandom(UUID.randomUUID().toString());
        scopeCode.setUsername(username);
        scopeCode.setScopes(OAuthUtils.encodeScopes(scopes));
        scopeCode.setCode(UUID.randomUUID().toString());
        return saveOrUpdate(scopeCode);
    }

}
