package com.harmony.kindless;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.harmony.kindless.core.service.SecurityService;
import com.harmony.kindless.core.service.UserService;
import com.harmony.kindless.oauth.OAuthDispatcher;
import com.harmony.kindless.oauth.handler.AuthorizationCodeOAuthRequestHandler;
import com.harmony.kindless.oauth.handler.ClientCredentialsOAuthRequestHandler;
import com.harmony.kindless.oauth.handler.ScopeCodeOAuthRequestHandler;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.oauth.service.ClientInfoService;
import com.harmony.kindless.oauth.service.ScopeCodeService;
import com.harmony.kindless.shiro.authc.JwtAuthenticatingFilter;
import com.harmony.kindless.shiro.listener.SessionAuthenticationListener;
import com.harmony.kindless.shiro.realm.JpaRealm;
import com.harmony.kindless.shiro.realm.JwtRealm;
import com.harmony.kindless.shiro.support.FailedFocusAuthenticationStrategy;
import com.harmony.kindless.shiro.support.FileSystemSessionDAO;
import com.harmony.kindless.shiro.support.KindlessSecurityManager;
import com.harmony.umbrella.data.repository.support.QueryableRepositoryFactoryBean;
import com.harmony.umbrella.web.filter.WebCurrentContextFilter;

/**
 * @author wuxii@foxmail.com
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = QueryableRepositoryFactoryBean.class)
public class KindlessApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(KindlessApplication.class, args);
    }

    @Bean
    FilterRegistrationBean<WebCurrentContextFilter> currentContextFilterBean() {
        FilterRegistrationBean<WebCurrentContextFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new WebCurrentContextFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    static class OAuthConfiguration {

        @Bean
        OAuthDispatcher oauthDispatcher(ClientInfoService clientService, ScopeCodeService scopeCodeService, AccessTokenService accessTokenService,
                UserService userService) {
            ScopeCodeOAuthRequestHandler scopeCodeHandler = new ScopeCodeOAuthRequestHandler();
            scopeCodeHandler.setClientInfoService(clientService);
            scopeCodeHandler.setScopeCodeService(scopeCodeService);
            scopeCodeHandler.setUserService(userService);
            AuthorizationCodeOAuthRequestHandler authorizationCodeHandler = new AuthorizationCodeOAuthRequestHandler();
            authorizationCodeHandler.setAccessTokenService(accessTokenService);
            authorizationCodeHandler.setScopeCodeService(scopeCodeService);
            ClientCredentialsOAuthRequestHandler clientHandler = new ClientCredentialsOAuthRequestHandler();
            clientHandler.setAccessTokenService(accessTokenService);
            clientHandler.setClientInfoService(clientService);
            return new OAuthDispatcher(scopeCodeHandler, authorizationCodeHandler, clientHandler);
        }

    }

    static class ShiroConfiguration {

        @Bean
        FilterRegistrationBean<Filter> shiroFilterBean(SecurityService securityService) throws Exception {
            FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
            filterRegistrationBean.setFilter(shiroFilter(securityService));
            filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
            filterRegistrationBean.setOrder(2);
            return filterRegistrationBean;
        }

        Filter shiroFilter(SecurityService securityService) throws Exception {
            ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

            factoryBean.setSecurityManager(securityManager(securityService));
            factoryBean.getFilters().put("token", new JwtAuthenticatingFilter(securityService));

            factoryBean.setLoginUrl("/login");
            factoryBean.setSuccessUrl("/");
            factoryBean.setUnauthorizedUrl("/unauthorized");

            Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
            // static resources
            filterChainDefinitionMap.put("/", "anon");
            filterChainDefinitionMap.put("/login", "anon");
            filterChainDefinitionMap.put("/logout", "anon");
            filterChainDefinitionMap.put("/static/**", "anon");
            filterChainDefinitionMap.put("/**/*.ico", "anon");
            filterChainDefinitionMap.put("/**/*.jpg", "anon");
            filterChainDefinitionMap.put("/**/*.js", "anon");
            filterChainDefinitionMap.put("/**/*.css", "anon");
            // biz resources
            filterChainDefinitionMap.put("/h2/**", "anon");
            filterChainDefinitionMap.put("/user/**", "anon");
            filterChainDefinitionMap.put("/session", "anon");
            // access token
            filterChainDefinitionMap.put("/oauth/authorize/**", "token");
            filterChainDefinitionMap.put("/oauth/token/**", "anon");
            filterChainDefinitionMap.put("/**", "token");
            factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
            return (Filter) factoryBean.getObject();
        }

        @Bean
        KindlessSecurityManager securityManager(SecurityService securityService) {
            KindlessSecurityManager ksm = new KindlessSecurityManager(JwtAuthenticatingFilter.DEFAULT_TOKEN_HEADER);

            DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
            sessionManager.setSessionDAO(new FileSystemSessionDAO("./target/sessions", true));
            ksm.setSessionManager(sessionManager);

            ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
            authenticator.setAuthenticationStrategy(new FailedFocusAuthenticationStrategy());
            authenticator.getAuthenticationListeners().add(new SessionAuthenticationListener(securityService));
            ksm.setAuthenticator(authenticator);

            ksm.setSecurityService(securityService);
            ksm.setRealms(Arrays.asList(new JpaRealm(securityService), new JwtRealm(securityService)));
            return ksm;
        }

        @Bean
        PasswordMatcher credentialsMatcher() {
            final PasswordMatcher credentialsMatcher = new PasswordMatcher();
            credentialsMatcher.setPasswordService(new DefaultPasswordService());
            return credentialsMatcher;
        }

        @Bean
        AuthorizationAttributeSourceAdvisor annotationAdvisor() {
            return new AuthorizationAttributeSourceAdvisor();
        }

        @Bean
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
            return new LifecycleBeanPostProcessor();
        }

    }
}
