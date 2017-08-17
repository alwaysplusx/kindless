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
     * @param userId
     *            用户Id
     * @param clientId
     *            客户端id
     * @param scope
     *            授予的权限
     * @return 临时编码
     */
    public ScopeCode grant(Long userId, String clientId, Set<String> scopes) {
        ScopeCode scopeCode = new ScopeCode();
        scopeCode.setCode(generateCode());
        scopeCode.setUserId(userId);
        scopeCode.setClientId(clientId);
        scopeCode.setCreatedTime(new Date());
        scopeCode.setExpiresIn(7200);
        scopeCode.setScopes(OAuthUtils.encodeScopes(scopes));
        return saveOrUpdate(scopeCode);
    }

    /**
     * 生成随机scope code
     * 
     * @return 随机scope code
     */
    protected String generateCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
