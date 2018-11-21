package com.harmony.kindless.user.service.impl;

import com.harmony.kindless.apis.domain.user.UserAuthority;
import com.harmony.kindless.user.repository.UserAuthorityRepository;
import com.harmony.kindless.user.service.UserAuthorityService;
import com.harmony.umbrella.data.model.SelectionModel;
import com.harmony.umbrella.data.query.CellResult;
import com.harmony.umbrella.data.query.Result;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuxii
 */
@Service
public class UserAuthorityServiceImpl extends ServiceSupport<UserAuthority, Long> implements UserAuthorityService {

    private final UserAuthorityRepository userAuthorityRepository;

    public UserAuthorityServiceImpl(UserAuthorityRepository userAuthorityRepository) {
        this.userAuthorityRepository = userAuthorityRepository;
    }

    @Override
    public List<String> getUserAuthorities(Long userId) {
        return queryWith()
                .equal("userId", userId)
                .execute()
                .getAllResult(SelectionModel.of("authority.code"))
                .stream()
                .map(Result::firstCellResult)
                .map(CellResult::stringValue)
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
