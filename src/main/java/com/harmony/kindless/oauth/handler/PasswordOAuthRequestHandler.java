package com.harmony.kindless.oauth.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.web.context.request.NativeWebRequest;

import com.harmony.kindless.domain.domain.User;
import com.harmony.kindless.domain.repository.UserRepository;
import com.harmony.kindless.oauth.OAuthRequestValidator;
import com.harmony.kindless.oauth.OAuthResponseWriter;
import com.harmony.kindless.oauth.domain.AccessToken;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.oauth.validator.UserValidator;

/**
 * @author wuxii@foxmail.com
 */
public class PasswordOAuthRequestHandler extends AbstractOAuthRequestHandler {

    private List<OAuthRequestValidator> validators;
    private UserRepository userRepository;
    private AccessTokenService accessTokenService;

    @Override
    public GrantType getSupportedGrantType() {
        return GrantType.PASSWORD;
    }

    @Override
    protected void doHandler(OAuthRequest request, NativeWebRequest webRequest) throws OAuthSystemException, IOException {
        AccessToken accessToken = accessTokenService.createAccessToken(new User(), null);
        OAuthResponseWriter//
                .bodyWriter(webRequest)
                .writeResponse(OAuthASResponse//
                        .tokenResponse(HttpServletResponse.SC_OK)//
                        .setAccessToken(accessToken.getAccessToken())//
                        .setExpiresIn(String.valueOf(accessToken.getExpiresIn()))//
                        .setRefreshToken(accessToken.getRefreshToken())//
                        .buildJSONMessage());
    }

    @Override
    public List<OAuthRequestValidator> getOAuthRequestValidators() {
        if (validators == null) {
            validators = new ArrayList<>();
            validators.add(new UserValidator(userRepository));
            validators = Collections.unmodifiableList(validators);
        }
        return validators;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
