package com.kindless.core.auditor.jwt;

import com.kindless.core.auditor.Auditor;

/**
 * @author wuxin
 */
public abstract class AbstractWebAuditorExtractor<T> {

    protected final String prefix;

    protected final String header;

    protected AuditorJwt auditorJwt;

    protected AbstractWebAuditorExtractor() {
        this(null);
    }

    protected AbstractWebAuditorExtractor(AuditorJwt auditorJwt) {
        this("Bearer ", "Authorization", auditorJwt);
    }

    protected AbstractWebAuditorExtractor(String prefix, String header, AuditorJwt auditorJwt) {
        this.prefix = prefix;
        this.auditorJwt = auditorJwt;
        this.header = header;
    }

    public Auditor extract(T request) {
        String token = extractAuthorizationToken(request);
        if (token == null || token.startsWith(prefix)) {
            return null;
        }
        return auditorJwt.parse(token.substring(prefix.length()));
    }

    protected abstract String extractAuthorizationToken(T request);

    public void setAuditorJwt(AuditorJwt auditorJwt) {
        this.auditorJwt = auditorJwt;
    }

}
