package com.harmony.kindless.core.service;

import com.harmony.kindless.apis.domain.core.UserAuthority;
import com.harmony.umbrella.data.service.Service;

import java.util.List;

/**
 * @author wuxii
 */
public interface UserAuthorityService extends Service<UserAuthority, Long> {

    List<String> getUserAuthorities(Long userId);

}
