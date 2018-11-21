package com.harmony.kindless;

import com.harmony.kindless.apis.domain.security.Authority;
import com.harmony.kindless.apis.domain.user.User;
import com.harmony.kindless.apis.domain.user.UserAuthority;
import com.harmony.kindless.security.repository.AuthorityRepository;
import com.harmony.kindless.user.repository.UserAuthorityRepository;
import com.harmony.kindless.user.repository.UserRepository;
import com.harmony.umbrella.data.repository.support.QueryableRepositoryFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author wuxii@foxmail.com
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = QueryableRepositoryFactoryBean.class)
public class KindlessApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(KindlessApplication.class, args);
    }

//    static class DatabaseInitConfiguration {
//
//        @Bean
//        CommandLineRunner init(UserRepository userRepository, AuthorityRepository authorityRepository, UserAuthorityRepository userAuthorityRepository) {
//            return (String... args) -> {
//                User user = new User();
//                user.setUsername("wuxii");
//                user.setPassword("123456");
//                user.setNickname("wuxii");
//                userRepository.save(user);
//
//                Authority authority = new Authority();
//                authority.setCode("user:read");
//                authority.setRemark("读取用户信息");
//                authorityRepository.save(authority);
//
//                UserAuthority userAuthority = new UserAuthority();
//                userAuthority.setUser(user);
//                userAuthority.setAuthority(authority);
//                userAuthorityRepository.save(userAuthority);
//            };
//        }
//
//    }
}


