package com.agao.security.cache;

import com.agao.utils.RedisClientUtils;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Agao
 * @date 2024/2/22 20:31
 */
@Slf4j
@Component
public class BlackSessionCache {
    @Autowired
    private RedisClientUtils redisClientUtils;
    /**
     * 黑名单缓存 结构是 key: sessionId, value: userId
     */
    private final LoadingCache<String, Optional<String>> loadingCache = CacheBuilder.newBuilder()
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Optional<String>>() {
                @Override
                public Optional<String> load(String key) throws Exception {
                    RMapCache<String, String> cache = redisClientUtils.getBlackSessionCache();
                    return Optional.ofNullable(cache.get(key));
                }
            });

    /**
     * 获取黑名单缓存
     *
     * @param sessionId sessionId
     * @return userId
     */
    public Optional<String> get(String sessionId) {
        try {
            return loadingCache.get(sessionId);
        } catch (ExecutionException e) {
            log.warn("BlackSessionCache obtain error, key[sessionId] is :{}", sessionId);
            return Optional.empty();
        }
    }

    /**
     * 设置黑名单session
     *
     * @param sessionId sessionId
     * @param userId    用户id
     */
    public void put(String sessionId, String userId) {
        RMapCache<String, String> cache = redisClientUtils.getBlackSessionCache();
        cache.put(sessionId, userId);
        loadingCache.invalidate(sessionId);
    }
}
