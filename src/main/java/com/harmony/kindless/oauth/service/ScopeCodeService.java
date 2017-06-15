package com.harmony.kindless.oauth.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.oltu.oauth2.common.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.oauth.domain.ClientInfo;
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

    public ScopeCode createScopeCode(String scope, ClientInfo clientInfo) {
        ScopeCode scopeCode = new ScopeCode();
        scopeCode.setScope(scope);
        scopeCode.setClientId(clientInfo.getClientId());
        scopeCode.setUsername("wuxii");
        scopeCode.setExpiresIn(3600);
        scopeCode.setRandom(UUID.randomUUID().toString());
        scopeCode.setCode(generate(clientInfo.getClientId(), "wuxii", scope, scopeCode.getRandom()));
        return scopeCodeRepository.save(scopeCode);
    }

    public String generate(String clientId, String username, String scope, String random) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(OAuth.OAUTH_CLIENT_ID, clientId);
        map.put(OAuth.OAUTH_USERNAME, username);
        map.put(OAuth.OAUTH_SCOPE, scope);
        map.put(OAuth.OAUTH_CODE, random);
        return digest(map);
    }

    protected String digest(Map<String, String> map) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }
        try {
            byte[] bytes = digest.digest(map.toString().getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }

}
