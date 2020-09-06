package com.kindless.config.auditor;

import com.harmony.umbrella.util.StringUtils;
import com.kindless.core.auditor.WebAuditorExtractor;
import com.kindless.core.auditor.jwt.AuditorJwt;
import com.kindless.core.auditor.jwt.JwtWebAuditorExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.UUID;

/**
 * @author wuxin
 */
@Slf4j
@Configuration
public class AuditorConfig {

    @Value("${kindless.web.jwt.secret:}")
    private String secret;

    @Value("${kindless.web.jwt.expires-in:2h}")
    private Duration expiresIn;

    @Bean
    public WebAuditorExtractor webAuditorExtractor() {
        return JwtWebAuditorExtractor
                .builder()
                .setAuditorJwt(auditorJwt())
                .build();
    }

    @Bean
    public AuditorJwt auditorJwt() {
        if (StringUtils.isBlank(secret)) {
            secret = UUID.randomUUID().toString();
            log.warn("Web jwt secret not been set, generate uuid as default: {}", secret);
        }
        return AuditorJwt
                .builder()
                .setSecret(secret)
                .setExpiresIn(expiresIn)
                .build();
    }

}
