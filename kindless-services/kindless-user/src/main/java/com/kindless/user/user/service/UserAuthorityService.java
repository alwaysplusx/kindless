package com.kindless.user.user.service;

import com.kindless.user.domain.UserAuthority;
import com.harmony.umbrella.data.service.Service;

import java.util.List;

/**
 * @author wuxii
 */
public interface UserAuthorityService extends Service<UserAuthority, Long> {

    List<String> getUserAuthorities(Long userId);

}
