package com.harmony.kindless.core.userdetails;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author wuxii
 */
@Component
public class RedisUserCache implements UserCache {

    private RedisTemplate<String, String> redisTemplate;

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
