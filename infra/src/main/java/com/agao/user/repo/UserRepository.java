package com.agao.user.repo;

import com.agao.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Agao
 * @date 2024/2/6 16:31
 */
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {
    User findFirstByUsername(String username);

    Integer countByUsername(String username);
}
