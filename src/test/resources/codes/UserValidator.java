//package com.harmony.kindless.oauth.validator;
//
//import org.apache.oltu.oauth2.as.request.OAuthRequest;
//import org.apache.oltu.oauth2.common.OAuth;
//import org.apache.oltu.oauth2.common.error.OAuthError;
//import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
//
//import com.harmony.kindless.domain.domain.User;
//import com.harmony.kindless.domain.repository.UserRepository;
//import com.harmony.kindless.oauth.OAuthRequestValidator;
//
///**
// * @author wuxii@foxmail.com
// */
//public class UserValidator implements OAuthRequestValidator {
//
//    private UserRepository userRepository;
//
//    public UserValidator(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public void valid(OAuthRequest request) throws OAuthProblemException {
//        String username = request.getParam(OAuth.OAUTH_USERNAME);
//        String password = request.getParam(OAuth.OAUTH_PASSWORD);
//        User user = userRepository.findByUsername(username);
//        if (user == null || !user.getPassword().equals(password)) {
//            throw OAuthProblemException.error(OAuthError.TokenResponse.INVALID_REQUEST, "invalid user");
//        }
//    }
//
//    public UserRepository getUserRepository() {
//        return userRepository;
//    }
//
//    public void setUserRepository(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//}
