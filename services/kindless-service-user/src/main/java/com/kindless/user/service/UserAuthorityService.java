package com.kindless.user.service;

import com.kindless.core.service.Service;
import com.kindless.user.domain.UserAuthority;

import java.util.List;

/**
 * @author wuxii
 */
public interface UserAuthorityService extends Service<UserAuthority, Long> {

    List<String> getUserAuthorities(Long userId);

}
