package com.harmony.kindless.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony.kindless.core.domain.Certificate;
import com.harmony.kindless.core.repository.CertificateRepository;
import com.harmony.kindless.core.service.CertificateService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;

/**
 * @author wuxii@foxmail.com
 */
@Service
public class CertificateServiceImpl extends ServiceSupport<Certificate, Long> implements CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    public CertificateServiceImpl() {
    }

    @Override
    public Certificate findByToken(String token) {
        return token != null ? certificateRepository.findByToken(token) : null;
    }

    @Override
    public String getSessionId(String token) {
        return null;
    }

    @Override
    public void bindSessionId(String token, String sessionId) {
    }

    // jwt service methods

    @Override
    protected QueryableRepository<Certificate, Long> getRepository() {
        return certificateRepository;
    }

}
