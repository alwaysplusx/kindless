package com.harmony.kindless;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.harmony.kindless.oauth.OAuthRequestDispatcher;
import com.harmony.kindless.oauth.handler.AuthorizationCodeOAuthRequestHandler;
import com.harmony.kindless.oauth.repository.ClientInfoRepository;
import com.harmony.kindless.oauth.repository.ScopeCodeRepository;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.umbrella.data.repository.support.QueryableRepositoryFactoryBean;
import com.harmony.umbrella.web.method.QueryBundleMethodArgumentResolver;
import com.harmony.umbrella.web.method.RequestResponseBundleMethodProcessor;

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
                argumentResolvers.add(new QueryBundleMethodArgumentResolver());
                argumentResolvers.add(new RequestResponseBundleMethodProcessor());
            }

            @Override
            public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
                returnValueHandlers.add(new RequestResponseBundleMethodProcessor());
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
        OAuthRequestDispatcher oauthDispatcher(//
                ClientInfoRepository clientInfoRepository, //
                ScopeCodeRepository scopeCodeRepository, //
                AccessTokenService accessTokenService//
        ) {
            AuthorizationCodeOAuthRequestHandler authorizationCodeHandler = new AuthorizationCodeOAuthRequestHandler();
            authorizationCodeHandler.setScopeCodeRepository(scopeCodeRepository);
            authorizationCodeHandler.setClientInfoRepository(clientInfoRepository);
            authorizationCodeHandler.setAccessTokenService(accessTokenService);
            return new OAuthRequestDispatcher(authorizationCodeHandler);
        }

    }

    /*@Configuration
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
            // anon
            filterChainDefinitionMap.put("/login",      "anon");
            filterChainDefinitionMap.put("/index",      "anon");
            filterChainDefinitionMap.put("/",           "anon");
            filterChainDefinitionMap.put("/index.html", "anon");
            filterChainDefinitionMap.put("/**",         "authc");
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

    }*/
}
