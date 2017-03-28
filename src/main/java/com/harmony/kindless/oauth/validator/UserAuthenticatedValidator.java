package com.harmony.kindless.oauth.validator;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;

import com.harmony.kindless.oauth.OAuthRequestValidator;

/**
 * @author wuxii@foxmail.com
 */
public class UserAuthenticatedValidator implements OAuthRequestValidator {

    private String loginUri;

    private String approvalUri;

    @Override
    public void valid(OAuthRequest request) throws OAuthProblemException {
        if (!isAuthenticated()) {
            throw OAuthProblemException//
                    .error(OAuthError.CodeResponse.ACCESS_DENIED)//
                    .description("unauthorized")//
                    .uri(loginUri)//
                    .scope(OAuthUtils.encodeScopes(request.getScopes()))//
                    .state(request.getParam("state"))//
                    .responseStatus(HttpServletResponse.SC_GONE);
        }
        if (!isApproval()) {
            throw OAuthProblemException//
                    .error(OAuthError.CodeResponse.ACCESS_DENIED)//
                    .description("unauthorized")//
                    .uri(approvalUri)//
                    .scope(OAuthUtils.encodeScopes(request.getScopes()))//
                    .state(request.getParam("state"))//
                    .responseStatus(HttpServletResponse.SC_GONE);
        }
    }

    private boolean isAuthenticated() {
        return true;
    }

    private boolean isApproval() {
        return true;
    }

    public String getLoginUri() {
        return loginUri;
    }

    public void setLoginUri(String loginUri) {
        this.loginUri = loginUri;
    }

    public String getApprovalUri() {
        return approvalUri;
    }

    public void setApprovalUri(String approvalUri) {
        this.approvalUri = approvalUri;
    }

}
