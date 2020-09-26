package com.kindless.user.service.impl;

import com.kindless.client.feign.dto.FindOrCreateUserRequest;
import com.kindless.core.WebRequestException;
import com.kindless.core.lock.ExecutableLockRegistry;
import com.kindless.core.service.ServiceSupport;
import com.kindless.domain.user.User;
import com.kindless.domain.user.UserAccount;
import com.kindless.user.repository.UserRepository;
import com.kindless.user.service.UserAccountService;
import com.kindless.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Path;
import java.util.Date;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author wuxii
 */
@Slf4j
@Service
@RequiredArgsConstructor
@RestController
public class UserServiceImpl extends ServiceSupport<User> implements UserService {

    private static final String LOCK_PATTERN_OF_CREATE_USER_BY_ACCOUNT = "kindless:user:creating-by-account:%s:%s";

    private final UserRepository userRepository;

    private final UserAccountService userAccountService;

    private final ExecutableLockRegistry lockRegistry;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findOrCreateUserByAccount(FindOrCreateUserRequest request) {
        String account = request.getAccount();
        int accountType = request.getAccountType();
        Supplier<User> userFinder = () -> userAccountService.findUserByAccount(account, accountType);
        String lockKey = String.format(LOCK_PATTERN_OF_CREATE_USER_BY_ACCOUNT, accountType, account);
        return doFindOrCreateUser(lockKey, userCreator(request), userFinder);
    }

    @Override
    public User register(User user) {
        log.info("do register user: {}", user);
        long existsUsernameCount = userRepository.count((root, query, cb) -> {
            Path<String> username = root.get("username");
            return cb.equal(username, user.getUsername());
        });
        if (existsUsernameCount > 0) {
            throw new WebRequestException("username exists");
        }
        log.info("user register success");
        return save(user);
    }

    private User doFindOrCreateUser(String lockKey, Supplier<User> userCreator, Supplier<User> userFinder) {
        User existsUser = userFinder.get();
        if (existsUser != null) {
            log.info("user already exists just return it. {}", existsUser);
            return existsUser;
        }
        return lockRegistry
                .obtainExecutableLock(lockKey)
                .execute(() -> {
                    User user = userFinder.get();
                    if (user != null) {
                        log.info("double check user, user exists just return it. {}", user);
                        return user;
                    }
                    return userCreator.get();
                });
    }

    private Supplier<User> userCreator(FindOrCreateUserRequest request) {
        return () -> this.doCreateUser(request);
    }

    private User doCreateUser(FindOrCreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setNickname(request.getNickname());
        user.setPassword(UUID.randomUUID().toString());
        user.setPasswordExpiredAt(new Date());
        user.setRemark("通过账号绑定自动创建的用户");
        save(user);

        UserAccount account = new UserAccount();
        account.setAccount(request.getAccount());
        account.setType(request.getAccountType());
        account.setStatus(UserAccount.STATUS_OF_PREPARE);
        account.setUser(user);
        userAccountService.bind(user, account);
        return user;
    }

    @Override
    protected PagingAndSortingRepository<User, Long> getRepository() {
        return userRepository;
    }

}
