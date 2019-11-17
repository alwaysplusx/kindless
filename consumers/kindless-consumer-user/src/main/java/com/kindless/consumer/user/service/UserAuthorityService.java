package com.kindless.consumer.user.service;

import com.harmony.umbrella.data.service.Service;
import com.kindless.provider.user.domain.UserAuthority;

import java.util.List;

/**
 * @author wuxii
 */
public interface UserAuthorityService extends Service<UserAuthority, Long> {

    List<String> getUserAuthorities(Long userId);

}
