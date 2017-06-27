package com.harmony.kindless;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.harmony.kindless.domain.service.UserService;
import com.harmony.kindless.oauth.OAuthDispatcher;
import com.harmony.kindless.oauth.handler.AuthorizationCodeOAuthRequestHandler;
import com.harmony.kindless.oauth.handler.ClientCredentialsOAuthRequestHandler;
import com.harmony.kindless.oauth.handler.CodeOAuthRequestHandler;
import com.harmony.kindless.oauth.handler.PasswordOAuthRequestHandler;
import com.harmony.kindless.oauth.handler.RefreshOAuthRequestHandler;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.oauth.service.ClientInfoService;
import com.harmony.kindless.oauth.service.ScopeCodeService;
import com.harmony.kindless.realm.JpaRealm;
import com.harmony.umbrella.data.repository.support.QueryableRepositoryFactoryBean;
import com.harmony.umbrella.web.method.support.BundleModelMethodArgumentResolver;
import com.harmony.umbrella.web.method.support.BundleParamMethodArgumentResolver;
import com.harmony.umbrella.web.method.support.BundleQueryMethodArgumentResolver;
import com.harmony.umbrella.web.method.support.BundleViewMethodProcessor;
import com.harmony.umbrella.web.servlet.handler.ModelFragmentInterceptor;

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
    WebMvcConfigurerAdapter webMvcConfigurer() {

        return new WebMvcConfigurerAdapter() {

            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
                argumentResolvers.add(new BundleParamMethodArgumentResolver());
                argumentResolvers.add(new BundleModelMethodArgumentResolver());
                argumentResolvers.add(new BundleQueryMethodArgumentResolver());
                argumentResolvers.add(new BundleViewMethodProcessor());
            }

            @Override
            public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
                returnValueHandlers.add(new BundleViewMethodProcessor());
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new ModelFragmentInterceptor());
            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*/**");
            }

        };
    }

    @Configuration
    public static class OAuth2Configuration {

        @Bean
        OAuthDispatcher oauthDispatcher(//
                ClientInfoService clientInfoService, //
                ScopeCodeService scopeCodeService, //
                AccessTokenService accessTokenService, //
                UserService userService//
        ) {

            AuthorizationCodeOAuthRequestHandler authorizationCodeHandler = new AuthorizationCodeOAuthRequestHandler();
            authorizationCodeHandler.setScopeCodeService(scopeCodeService);
            authorizationCodeHandler.setClientInfoService(clientInfoService);
            authorizationCodeHandler.setAccessTokenService(accessTokenService);

            ClientCredentialsOAuthRequestHandler clientCredentialsHandler = new ClientCredentialsOAuthRequestHandler();

            CodeOAuthRequestHandler codeHandler = new CodeOAuthRequestHandler();
            codeHandler.setClientInfoService(clientInfoService);
            codeHandler.setScopeCodeService(scopeCodeService);

            PasswordOAuthRequestHandler passwordHandler = new PasswordOAuthRequestHandler();
            passwordHandler.setAccessTokenService(accessTokenService);
            passwordHandler.setUserService(userService);

            RefreshOAuthRequestHandler refreshHandler = new RefreshOAuthRequestHandler();
            refreshHandler.setAccessTokenService(accessTokenService);
            refreshHandler.setClientInfoService(clientInfoService);

            return new OAuthDispatcher(authorizationCodeHandler, clientCredentialsHandler, codeHandler, passwordHandler, refreshHandler);
        }

    }

    @Configuration
    public static class ShiroConfiguration {

        @Bean
        FilterRegistrationBean webFilter() {
            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
            filterRegistrationBean.setFilter(new DelegatingFilterProxy("shiroFilter"));
            filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
            return filterRegistrationBean;
        }

        @Bean
        ShiroFilterFactoryBean shiroFilter() {
            ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
            factoryBean.setSecurityManager(securityManager());
            factoryBean.setLoginUrl("/login");
            factoryBean.setSuccessUrl("/index");
            factoryBean.setUnauthorizedUrl("/unauthorized");
            Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
            // static resources
            filterChainDefinitionMap.put("/static/**",  "anon");
            filterChainDefinitionMap.put("/**/*.ico",   "anon");
            filterChainDefinitionMap.put("/**/*.jpg",   "anon");
            filterChainDefinitionMap.put("/**/*.js",    "anon");
            filterChainDefinitionMap.put("/**/*.css",   "anon");
            // anon
            filterChainDefinitionMap.put("/",           "anon");
            filterChainDefinitionMap.put("/login",      "anon");
            filterChainDefinitionMap.put("/index",      "anon");
            filterChainDefinitionMap.put("/index.html", "anon");
            filterChainDefinitionMap.put("/order/**",   "anon");
            filterChainDefinitionMap.put("/h2/**",      "anon");
            filterChainDefinitionMap.put("/error/**",   "anon");
            filterChainDefinitionMap.put("/**",         "anon");
            factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
            return factoryBean;
        }

        @Bean(name = "securityManager")
        DefaultWebSecurityManager securityManager() {
            final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
            securityManager.setSessionManager(new DefaultWebSessionManager());
            securityManager.setRealm(jpaRealm());
            return securityManager;
        }

        @Bean
        JpaRealm jpaRealm() {
            return new JpaRealm();
        }

        @Bean(name = "credentialsMatcher")
        PasswordMatcher credentialsMatcher() {
            final PasswordMatcher credentialsMatcher = new PasswordMatcher();
            credentialsMatcher.setPasswordService(new DefaultPasswordService());
            return credentialsMatcher;
        }

        @Bean
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
            return new LifecycleBeanPostProcessor();
        }

    }
}
