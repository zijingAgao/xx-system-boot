package com.agao.utils;

import lombok.Getter;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Agao
 * @date 2024/2/26 22:06
 */
@Getter
@Component
public class RedisClientUtils {
    private final RedissonClient redissonClient;
    /**
     * 黑名单session缓存key
     */
    private static final String BLACK_SESSION_CACHE = "auth_black_session_cache";
    /**
     * 黑名单session缓存value 缓存结构 <key,<sessionId,userId>>
     */
    private final RMapCache<String, String> blackSessionCache;


    @Autowired
    public RedisClientUtils(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;

        blackSessionCache = redissonClient.getMapCache(BLACK_SESSION_CACHE);
    }
}
