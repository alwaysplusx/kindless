package com.kindless.core.auditor.jwt;

import com.kindless.core.auditor.Auditor;
import com.kindless.core.auditor.WebAuditorExtractor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxin
 */
@Builder(setterPrefix = "set")
@Setter
public class JwtWebAuditorExtractor implements WebAuditorExtractor {

    @Builder.Default
    private String prefix = "Bearer ";

    @Builder.Default
    private String header = "Authorization";

    @NonNull
    private AuditorJwt auditorJwt;

    @Override
    public Auditor extract(HttpServletRequest request) {
        String authorizationToken = request.getHeader(this.header);
        if (authorizationToken == null || !authorizationToken.startsWith(prefix)) {
            return null;
        }
        String token = authorizationToken.substring(prefix.length());
        return auditorJwt.parse(token);
    }

}
