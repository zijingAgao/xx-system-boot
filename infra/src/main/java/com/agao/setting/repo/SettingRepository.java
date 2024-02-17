package com.agao.setting.repo;

import com.agao.setting.entity.Setting;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Agao
 * @date 2024/2/17 21:05
 */
public interface SettingRepository extends MongoRepository<Setting, String> {
    /**
     * 根据key查询
     *
     * @param key key
     * @return setting项
     */
    Setting findByKey(String key);
}
