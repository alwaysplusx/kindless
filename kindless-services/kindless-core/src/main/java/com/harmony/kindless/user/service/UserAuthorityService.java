package com.harmony.kindless.user.service;

import com.harmony.kindless.apis.domain.user.UserAuthority;
import com.harmony.umbrella.data.service.Service;

import java.util.List;

/**
 * @author wuxii
 */
public interface UserAuthorityService extends Service<UserAuthority, Long> {

    List<String> getUserAuthorities(Long userId);

}
