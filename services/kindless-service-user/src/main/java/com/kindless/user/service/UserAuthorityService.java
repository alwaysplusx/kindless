package com.kindless.user.service;

import com.kindless.core.service.Service;
import com.kindless.domain.user.UserAuthority;

import java.util.List;

/**
 * @author wuxii
 */
public interface UserAuthorityService extends Service<UserAuthority> {

    List<String> getUserAuthorities(Long userId);

}
