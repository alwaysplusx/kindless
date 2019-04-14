package com.harmony.kindless.user.userdetails;

import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author wuxii
 */
public class RedisUserCache implements UserCache {

    // private RedisTemplate<String, String> redisTemplate;

    @Override
    public UserDetails getUserFromCache(String username) {
        return null;
    }

    @Override
    public void putUserInCache(UserDetails user) {

    }

    @Override
    public void removeUserFromCache(String username) {

    }

}
