package com.harmony.kindless;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.harmony.umbrella.data.support.QueryableRepositoryFactoryBean;
import com.harmony.umbrella.web.method.QueryBundleMethodArgumentResolver;
import com.harmony.umbrella.web.method.ResponseBundleReturnValueHandler;

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
    RequestMappingHandlerAdapter configRequestMappingHandler(RequestMappingHandlerAdapter handler) {
        List<HandlerMethodReturnValueHandler> copy = new ArrayList<>();
        List<HandlerMethodReturnValueHandler> returnValueHandlers = handler.getReturnValueHandlers();
        for (HandlerMethodReturnValueHandler h : returnValueHandlers) {
            if (!(h instanceof RequestResponseBodyMethodProcessor)) {
                copy.add(h);
            }
        }
        handler.setReturnValueHandlers(copy);
        return handler;
    }

    @Bean
    WebMvcConfigurerAdapter webMvcConfigurer() {
        return new WebMvcConfigurerAdapter() {

            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
                argumentResolvers.add(new QueryBundleMethodArgumentResolver());
            }

            @Override
            public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
                returnValueHandlers.add(new ResponseBundleReturnValueHandler());
            }
        };
    }

    /*@Configuration
    public static class ShiroConfiguration {
    
        @Bean
        public FilterRegistrationBean webFilter() {
            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
            filterRegistrationBean.setFilter(new DelegatingFilterProxy("shiroFilter"));
            filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
            return filterRegistrationBean;
        }
    
        @Bean
        public ShiroFilterFactoryBean shiroFilter() {
            ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
            factoryBean.setSecurityManager(securityManager());
            factoryBean.setLoginUrl("/login");
            factoryBean.setSuccessUrl("/index");
            factoryBean.setUnauthorizedUrl("/unauthorized");
            Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
            filterChainDefinitionMap.put("/login", "anon");
            filterChainDefinitionMap.put("/index", "anon");
            filterChainDefinitionMap.put("/", "anon");
            filterChainDefinitionMap.put("/index.html", "anon");
            filterChainDefinitionMap.put("/**", "authc");
            factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
            return factoryBean;
        }
    
        @Bean(name = "securityManager")
        public DefaultWebSecurityManager securityManager() {
            final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
            securityManager.setRealm(realm());
            securityManager.setSessionManager(sessionManager());
            return securityManager;
        }
    
        @Bean
        public DefaultWebSessionManager sessionManager() {
            final DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
            return sessionManager;
        }
    
        @Bean
        public Realm realm() {
            return new JpaRealm();
        }
    
        @Bean(name = "credentialsMatcher")
        public PasswordMatcher credentialsMatcher() {
            final PasswordMatcher credentialsMatcher = new PasswordMatcher();
            credentialsMatcher.setPasswordService(passwordService());
            return credentialsMatcher;
        }
    
        @Bean(name = "passwordService")
        public DefaultPasswordService passwordService() {
            return new DefaultPasswordService();
        }
    
        @Bean
        public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
            return new LifecycleBeanPostProcessor();
        }
    
    }*/
}
