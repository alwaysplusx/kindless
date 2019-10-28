package com.kindless.user.user.service.impl;

import com.kindless.user.domain.UserAuthority;
import com.kindless.user.user.repository.UserAuthorityRepository;
import com.kindless.user.user.service.UserAuthorityService;
import com.harmony.umbrella.data.Selections;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.result.CellValue;
import com.harmony.umbrella.data.result.RowResult;
import com.harmony.umbrella.data.service.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuxii
 */
@Service
public class UserAuthorityServiceImpl extends ServiceSupport<UserAuthority, Long> implements UserAuthorityService {

    private final UserAuthorityRepository userAuthorityRepository;

    @Autowired
    public UserAuthorityServiceImpl(UserAuthorityRepository userAuthorityRepository) {
        this.userAuthorityRepository = userAuthorityRepository;
    }

    @Cacheable(cacheNames = "user:authorities", key = "#p0")
    @Override
    public List<String> getUserAuthorities(Long userId) {
        return queryWith()
                .equal("userId", userId)
                .execute()
                .getAllResult(Selections.of("authority.code"))
                .stream()
                .map(RowResult::firstCellResult)
                .map(CellValue::stringValue)
                .collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = "user:authorities", key = "#p0.userId ?: #p0.user.id")
    @Override
    public UserAuthority saveOrUpdate(UserAuthority entity) {
        return super.saveOrUpdate(entity);
    }

    @Override
    protected QueryableRepository<UserAuthority, Long> getRepository() {
        return userAuthorityRepository;
    }

    @Override
    protected Class<UserAuthority> getDomainClass() {
        return UserAuthority.class;
    }

}
