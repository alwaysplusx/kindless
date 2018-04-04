package com.harmony.kindless.oauth.service.impl;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.core.domain.ClientInfo;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.oauth.domain.ScopeCode;
import com.harmony.kindless.oauth.repository.ScopeCodeRepository;
import com.harmony.kindless.oauth.service.ScopeCodeService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class ScopeCodeServiceImpl extends ServiceSupport<ScopeCode, Long> implements ScopeCodeService {

    @Autowired
    private ScopeCodeRepository scopeCodeRepository;

    private int expiresIn = 7200;

    @Override
    public ScopeCode findByCode(String code) {
        return code == null ? null : scopeCodeRepository.findByCode(code);
    }

    @Override
    public ScopeCode grant(User user, ClientInfo client, Set<String> scopes) {
        ScopeCode scopeCode = new ScopeCode();
        scopeCode.setCode(generateCode());
        scopeCode.setUser(user);
        scopeCode.setClientInfo(client);
        scopeCode.setIssuedAt(new Date());
        scopeCode.setExpiresIn(expiresIn);
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

    @Override
    protected QueryableRepository<ScopeCode, Long> getRepository() {
        return scopeCodeRepository;
    }

}
