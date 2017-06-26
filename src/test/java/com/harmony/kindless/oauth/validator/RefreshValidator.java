//package com.harmony.kindless.oauth.validator;
//
//import org.apache.oltu.oauth2.as.request.OAuthRequest;
//import org.apache.oltu.oauth2.common.OAuth;
//import org.apache.oltu.oauth2.common.error.OAuthError;
//import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
//
//import com.harmony.kindless.oauth.OAuthRequestValidator;
//import com.harmony.kindless.oauth.domain.AccessToken;
//import com.harmony.kindless.oauth.repository.AccessTokenRepository;
//
///**
// * @author wuxii@foxmail.com
// */
//public class RefreshValidator implements OAuthRequestValidator {
//
//    private AccessTokenRepository accessTokenRepository;
//
//    public RefreshValidator(AccessTokenRepository accessTokenRepository) {
//        this.accessTokenRepository = accessTokenRepository;
//    }
//
//    @Override
//    public void valid(OAuthRequest request) throws OAuthProblemException {
//        String clientId = request.getClientId();
//        String refreshToken = request.getParam(OAuth.OAUTH_REFRESH_TOKEN);
//        AccessToken accessToken = accessTokenRepository.findByClientIdAndRefreshToken(clientId, refreshToken);
//        if (accessToken == null || isExpired(accessToken)) {
//            throw OAuthProblemException.error(OAuthError.TokenResponse.INVALID_REQUEST, "token expired");
//        }
//    }
//
//    protected boolean isExpired(AccessToken accessToken) {
//        return false;
//    }
//
//    public AccessTokenRepository getAccessTokenRepository() {
//        return accessTokenRepository;
//    }
//
//    public void setAccessTokenRepository(AccessTokenRepository accessTokenRepository) {
//        this.accessTokenRepository = accessTokenRepository;
//    }
//
//}
