package com.agao.security.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Agao
 * @date 2024/2/22 21:17
 */
@Slf4j
@Component
public class LoginExpiredCache {
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 登录过期缓存 结构 key: 用户id, value: 过期时间
     */
    private final LoadingCache<String, Optional<Long>> loadingCache =
            CacheBuilder.newBuilder()
                    .expireAfterAccess(30, TimeUnit.MINUTES)
                    .build(new CacheLoader<String, Optional<Long>>() {
                        @Override
                        public Optional<Long> load(String key) throws Exception {
                            RBucket<Long> bucket = redissonClient.getBucket(key);
                            return Optional.ofNullable(bucket.get());
                        }
                    });

    public Optional<Long> get(String key) {
        try {
            return loadingCache.get(key);
        } catch (ExecutionException e) {
            log.error("LoginExpiredCache obtain error, key is :{}", key);
            return Optional.empty();
        }
    }

    public void put(String key, Long timestamp) {
        loadingCache.put(key, Optional.ofNullable(timestamp));
    }

    public void logoutUserForLogin(String userId,long timestamp, long expireTimeMs) {
        RBucket<Long> bucket = redissonClient.getBucket(userId);
        bucket.set(timestamp, expireTimeMs, TimeUnit.MILLISECONDS);
        loadingCache.invalidate(userId);
    }

}
