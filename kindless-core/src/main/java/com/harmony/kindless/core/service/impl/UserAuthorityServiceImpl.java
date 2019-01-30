package com.harmony.kindless.core.service.impl;

import com.harmony.kindless.apis.domain.core.UserAuthority;
import com.harmony.kindless.core.service.UserAuthorityService;
import com.harmony.kindless.core.repository.UserAuthorityRepository;
import com.harmony.umbrella.data.Selections;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.result.CellValue;
import com.harmony.umbrella.data.result.RowResult;
import com.harmony.umbrella.data.service.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    protected QueryableRepository<UserAuthority, Long> getRepository() {
        return userAuthorityRepository;
    }

    @Override
    protected Class<UserAuthority> getDomainClass() {
        return UserAuthority.class;
    }
}
