package com.harmony.kindless.core.service;

import com.harmony.kindless.apis.domain.core.User;
import com.harmony.kindless.apis.dto.UserSecurityData;
import com.harmony.umbrella.data.service.Service;

/**
 * @author wuxii
 */
public interface UserService extends Service<User, Long> {

    User getByUsername(String username);

    UserSecurityData getUserSecurityData(Long userId, String username);

}
