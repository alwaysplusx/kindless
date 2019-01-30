package com.harmony.kindless.moment.web;

import com.harmony.kindless.apis.dto.CurrentUser;
import com.harmony.umbrella.security.jwt.JwtUserDetails;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author wuxii
 */
public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == CurrentUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();

        //
        CurrentUser.CurrentUserBuilder builder = CurrentUser.builder();
        if (principal instanceof UserDetails) {
            builder.username(((UserDetails) principal).getUsername());
        }
        if (principal instanceof JwtUserDetails) {
            builder.userId(((JwtUserDetails) principal).getId());
        }
        return builder.build();
    }

}
