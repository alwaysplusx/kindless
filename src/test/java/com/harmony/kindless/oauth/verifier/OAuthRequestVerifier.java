package com.harmony.kindless.oauth.verifier;

import org.apache.oltu.oauth2.as.request.OAuthRequest;

/**
 * @author wuxii@foxmail.com
 */
public interface OAuthRequestVerifier {

    VerifierResult verify(OAuthRequest request);

    public static class VerifierResult {

        public boolean isOK() {
            return true;
        }

        public Exception getIssues() {
            return null;
        }

    }

}
