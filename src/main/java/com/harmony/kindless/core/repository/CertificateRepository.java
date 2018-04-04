package com.harmony.kindless.core.repository;

import org.springframework.stereotype.Repository;

import com.harmony.kindless.core.domain.Certificate;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii@foxmail.com
 */
@Repository
public interface CertificateRepository extends QueryableRepository<Certificate, Long> {

    Certificate findByToken(String token);

}
