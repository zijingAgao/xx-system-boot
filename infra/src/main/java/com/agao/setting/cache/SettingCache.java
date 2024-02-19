package com.agao.setting.cache;

import com.agao.setting.entity.Setting;
import com.agao.setting.repo.SettingRepository;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

/**
 * @author Agao
 * @date 2024/2/17 21:07
 */
@Slf4j
@Component
public class SettingCache {
    public static final String LIST_SPLITTER = ";,";
    @Autowired
    private SettingRepository settingRepository;

    private final LoadingCache<String, Optional<Setting>> SettingLoadingCache = CacheBuilder.newBuilder().build(
            new CacheLoader<String, Optional<Setting>>() {
                @Override
                public Optional<Setting> load(String key) throws Exception {
                    Setting setting = settingRepository.findByKey(key);
                    if (setting == null) {
                        return Optional.empty();
                    }
                    return Optional.of(setting);
                }
            });

    /**
     * 获取配置
     *
     * @param key key
     * @return setting
     */
    private Optional<Setting> get(String key) {
        try {
            return SettingLoadingCache.get(key);
        } catch (ExecutionException e) {
            log.error("getSetting error", e);
            return Optional.empty();
        }
    }

    private <T> T getConfigValueAs(String key, T defaultValue, Function<String, T> converter) {
        Optional<Setting> setting = get(key);
        if (setting.isPresent()) {
            return converter.apply(setting.get().getValue());
        }
        return defaultValue;
    }

    public String getConfigValue(String key, String defaultValue) {
        Optional<Setting> setting = get(key);
        if (setting.isPresent()) {
            return setting.get().getValue();
        }
        return defaultValue;
    }

    public String getConfigValueAsInt(String key, String defaultValue) {
        Optional<Setting> setting = get(key);
        if (setting.isPresent()) {
            return setting.get().getValue();
        }
        return defaultValue;
    }

    public int getConfigValueAsInt(String cfgKey, int defaultValue) {
        return getConfigValueAs(cfgKey, defaultValue, Integer::parseInt);
    }

    public long getConfigValueAsLong(String cfgKey, long defaultValue) {
        return getConfigValueAs(cfgKey, defaultValue, Long::parseLong);
    }

    public boolean getConfigValueAsBoolean(String cfgKey, boolean defaultValue) {
        return getConfigValueAs(cfgKey, defaultValue, Boolean::parseBoolean);
    }

    public double getConfigValueAsDouble(String cfgKey, double defaultValue) {
        return getConfigValueAs(cfgKey, defaultValue, Double::parseDouble);
    }

    public List<String> getConfigValueAsList(String cfgKey, List<String> defaultValue) {
        return getConfigValueAs(cfgKey, defaultValue, s -> Splitter.on(CharMatcher.anyOf(LIST_SPLITTER)).splitToList(s));
    }

    public float getConfigValueAsFloat(String cfgKey, float defaultValue) {
        return getConfigValueAs(cfgKey, defaultValue, Float::parseFloat);
    }

    /**
     * 失效单个key
     *
     * @param key key
     */
    public void invalidateKey(String key) {
        SettingLoadingCache.invalidate(key);
    }

    /**
     * 失效setting缓存
     */
    public void invalidateAll() {
        SettingLoadingCache.invalidateAll();
    }


}
