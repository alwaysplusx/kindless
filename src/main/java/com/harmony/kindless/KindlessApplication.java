package com.harmony.kindless;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.harmony.kindless.context.filter.ShiroCurrentContextFilter;
import com.harmony.kindless.core.domain.Menu;
import com.harmony.kindless.core.domain.User;
import com.harmony.kindless.core.repository.MenuRepository;
import com.harmony.kindless.core.repository.UserRepository;
import com.harmony.kindless.core.service.RoleService;
import com.harmony.kindless.core.service.UserService;
import com.harmony.kindless.jwt.JwtTokenFinder;
import com.harmony.kindless.jwt.JwtTokenService;
import com.harmony.kindless.jwt.support.HttpHeaderTokenFinder;
import com.harmony.kindless.oauth.OAuthDispatcher;
import com.harmony.kindless.oauth.handler.AuthorizationCodeOAuthRequestHandler;
import com.harmony.kindless.oauth.handler.ClientCredentialsOAuthRequestHandler;
import com.harmony.kindless.oauth.handler.ScopeCodeOAuthRequestHandler;
import com.harmony.kindless.oauth.service.AccessTokenService;
import com.harmony.kindless.oauth.service.ClientInfoService;
import com.harmony.kindless.oauth.service.ScopeCodeService;
import com.harmony.kindless.shiro.AuthorizationService;
import com.harmony.kindless.shiro.authc.JwtAuthenticatingFilter;
import com.harmony.kindless.shiro.realm.JpaRealm;
import com.harmony.kindless.shiro.realm.UserRealm;
import com.harmony.kindless.shiro.support.FailedFocusAuthenticationStrategy;
import com.harmony.kindless.shiro.support.FileSystemSessionDAO;
import com.harmony.kindless.shiro.support.KindlessSecurityManager;
import com.harmony.kindless.shiro.support.SimpleAuthorizationService;
import com.harmony.umbrella.data.repository.support.QueryableRepositoryFactoryBean;
import com.harmony.umbrella.json.Json;
import com.harmony.umbrella.util.IOUtils;
import com.harmony.umbrella.util.PatternResourceFilter;

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
    FilterRegistrationBean<Filter> corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new CorsFilter(source));
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        filterRegistrationBean.setName("corsFilter");
        filterRegistrationBean.setOrder(0);
        return filterRegistrationBean;
    }

    @Bean
    FilterRegistrationBean<Filter> currentContextFilterBean() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        PatternResourceFilter<String> resourceFilter = new PatternResourceFilter<>();
        // XXX static resources. and so on
        resourceFilter.addExcludes("**/*.js");
        resourceFilter.addExcludes("**/*.png");
        resourceFilter.addExcludes("**/*.jpg");
        resourceFilter.addExcludes("**/*.css");
        resourceFilter.addExcludes("**/*.gif");

        ShiroCurrentContextFilter filter = new ShiroCurrentContextFilter();
        filter.setResourceFilter(resourceFilter);

        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        filterRegistrationBean.setName("currentContextFilter");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    // @Bean
    // ServletRegistrationBean<Servlet> statViewServlet() {
    // ServletRegistrationBean<Servlet> registrationBean = new ServletRegistrationBean<>();
    // registrationBean.setUrlMappings(Arrays.asList("/druid/*"));
    // registrationBean.setName("druidStatViewServlet");
    // registrationBean.setServlet(new StatViewServlet());
    // return registrationBean;
    // }

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
        FilterRegistrationBean<Filter> shiroFilterBean(SecurityManager securityManager, JwtTokenFinder jwtTokenFinder) throws Exception {
            FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
            filterRegistrationBean.setFilter(shiroFilter(securityManager, jwtTokenFinder));
            filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
            filterRegistrationBean.setName("shiroFilter");
            filterRegistrationBean.setOrder(2);
            return filterRegistrationBean;
        }

        Filter shiroFilter(SecurityManager securityManager, JwtTokenFinder jwtTokenFinder) throws Exception {
            ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

            factoryBean.setSecurityManager(securityManager);
            factoryBean.getFilters().put("jwt", new JwtAuthenticatingFilter(jwtTokenFinder));

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
            filterChainDefinitionMap.put("/druid/**", "anon");
            filterChainDefinitionMap.put("/h2/**", "anon");
            filterChainDefinitionMap.put("/user/**", "anon");
            filterChainDefinitionMap.put("/session", "anon");
            // access token
            filterChainDefinitionMap.put("/oauth/token/**", "anon");
            filterChainDefinitionMap.put("/oauth/authorize/**", "jwt");
            filterChainDefinitionMap.put("/**", "jwt");
            factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
            return (Filter) factoryBean.getObject();
        }

        @Bean
        AuthorizationService authorizationService(UserService userService, RoleService roleService) {
            SimpleAuthorizationService authorizationService = new SimpleAuthorizationService();
            authorizationService.setRoleService(roleService);
            authorizationService.setUserService(userService);
            return authorizationService;
        }

        @Bean
        KindlessSecurityManager securityManager(JwtTokenService jwtTokenService, JwtTokenFinder jwtTokenFinder, AuthorizationService authorizationService) {
            KindlessSecurityManager ksm = new KindlessSecurityManager();

            DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
            sessionManager.setSessionDAO(new FileSystemSessionDAO("./target/sessions", true));
            ksm.setSessionManager(sessionManager);

            ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
            authenticator.setAuthenticationStrategy(new FailedFocusAuthenticationStrategy());
            ksm.setAuthenticator(authenticator);

            ksm.setJwtTokenFinder(jwtTokenFinder);
            ksm.setJwtTokenService(jwtTokenService);

            JpaRealm jpaRealm = new JpaRealm();
            jpaRealm.setAuthorizationService(authorizationService);

            UserRealm userRealm = new UserRealm();
            userRealm.setAuthorizationService(authorizationService);
            userRealm.setJwtTokenService(jwtTokenService);

            ksm.setRealms(Arrays.asList(jpaRealm, userRealm));
            return ksm;
        }

        @Bean
        JwtTokenFinder jwtTokenFinder() {
            return new HttpHeaderTokenFinder();
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

    static class DataImportConfiguration {

        @Bean
        CommandLineRunner importDatas(MenuRepository menuRepository, UserRepository userRepository) {
            Data data = getData();
            return (String... args) -> {
                data.getMenus().forEach(menuRepository::save);
                data.getUsers().forEach(userRepository::save);
            };
        }

        private Data getData() {
            return Json.parse(getProperties(), Data.class);
        }

        private String getProperties() {
            ClassPathResource resource = new ClassPathResource("data.json");
            try (InputStream is = resource.getInputStream()) {
                return IOUtils.toString(is);
            } catch (IOException e) {
                ReflectionUtils.rethrowRuntimeException(e);
                return null;
            }
        }

        public static class Data {

            private List<User> users;
            private List<Menu> menus;

            public List<User> getUsers() {
                return users;
            }

            public void setUsers(List<User> users) {
                this.users = users;
            }

            public List<Menu> getMenus() {
                return menus;
            }

            public void setMenus(List<Menu> menus) {
                this.menus = menus;
            }

        }

    }

}
