package com.harmony.kindless.core.service.impl;

import com.harmony.kindless.apis.CodeException;
import com.harmony.kindless.apis.ResponseCodes;
import com.harmony.kindless.apis.domain.core.User;
import com.harmony.kindless.apis.dto.UserSecurityData;
import com.harmony.kindless.core.repository.UserRepository;
import com.harmony.kindless.core.service.UserAuthorityService;
import com.harmony.kindless.core.service.UserService;
import com.harmony.umbrella.data.JpaQueryBuilder;
import com.harmony.umbrella.data.Selections;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuxii
 */
@Primary
@Service
public class UserServiceImpl extends ServiceSupport<User, Long> implements UserService {

    private final UserRepository userRepository;

    private final UserAuthorityService userAuthorityService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserAuthorityService userAuthorityService) {
        this.userRepository = userRepository;
        this.userAuthorityService = userAuthorityService;
    }

    @Override
    public User getByUsername(String username) {
        return queryWith()
                .equal("username", username)
                .getSingleResult()
                .orElseThrow(ResponseCodes.NOT_FOUND::toException);
    }

    @Override
    public UserSecurityData getUserSecurityData(Long userId, String username) {
        if (StringUtils.isBlank(username) && (userId == null || userId == 0)) {
            throw new CodeException(ResponseCodes.ILLEGAL_ARGUMENT);
        }
        JpaQueryBuilder<User> builder = queryWith();
        if (userId != null && userId > 0) {
            builder.equal("id", userId);
        }
        if (StringUtils.isNotBlank(username)) {
            builder.equal("username", username);
        }
        Selections selections = Selections.of("id", "username", "password", "passwordExpiredAt", "accountStatus", "accountExpiredAt");
        return builder
                .execute()
                .getSingleResult(selections)
                .mapToEntity(User.class)
                .map(this::buildUserSecurityData)
                .orElseThrow(ResponseCodes.NOT_FOUND::toException);
    }

    private UserSecurityData buildUserSecurityData(User user) {
        long now = System.currentTimeMillis();
        List<String> userAuthorities = userAuthorityService.getUserAuthorities(user.getId());
        return UserSecurityData
                .builder()
                .userId(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .accountExpired(now > user.getAccountExpiredAt().getTime())
                .passwordExpired(now > user.getPasswordExpiredAt().getTime())
                .accountEnabled(user.isEnabled())
                .authorities(userAuthorities)
                .build();
    }

    @Override
    protected QueryableRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    protected Class<User> getDomainClass() {
        return userRepository.getDomainClass();
    }


}
