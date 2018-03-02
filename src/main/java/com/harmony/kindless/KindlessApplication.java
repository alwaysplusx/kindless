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
import com.harmony.kindless.shiro.filter.JwtAuthenticatingFilter;
import com.harmony.kindless.shiro.realm.JpaRealm;
import com.harmony.kindless.shiro.realm.JwtRealm;
import com.harmony.kindless.shiro.support.FailedFocusAuthenticationStrategy;
import com.harmony.kindless.shiro.support.FileSystemSessionDAO;
import com.harmony.kindless.shiro.support.KindlessSecurityManager;
import com.harmony.umbrella.data.repository.support.QueryableRepositoryFactoryBean;

/**
 * @author wuxii@foxmail.com
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = QueryableRepositoryFactoryBean.class)
public class KindlessApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(KindlessApplication.class, args);
    }

    static class ShiroConfiguration {

        @Bean
        FilterRegistrationBean shiroFilterBean(SecurityService securityService) throws Exception {
            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
            filterRegistrationBean.setFilter(shiroFilter(securityService));
            filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
            filterRegistrationBean.setOrder(1);
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
            filterChainDefinitionMap.put("/h2/**", "anon");
            filterChainDefinitionMap.put("/user/**", "anon");
            filterChainDefinitionMap.put("/session", "anon");
            filterChainDefinitionMap.put("/**", "token");
            factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
            return (Filter) factoryBean.getObject();
        }

        @Bean
        KindlessSecurityManager securityManager(SecurityService securityService) {
            KindlessSecurityManager ksm = new KindlessSecurityManager(JwtAuthenticatingFilter.DEFAULT_TOKEN_HEADER);

            DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
            sessionManager.setSessionDAO(new FileSystemSessionDAO("./target/sessions"));
            ksm.setSessionManager(sessionManager);

            ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
            authenticator.setAuthenticationStrategy(new FailedFocusAuthenticationStrategy());
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
