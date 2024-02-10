package com.agao.repo;

import com.agao.entity.user.User;
import com.agao.ro.user.UserQueryRo;
import com.agao.utils.MongoPageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author Agao
 * @date 2024/2/10 23:46
 */
public class UserRepositoryImpl implements UserRepositoryCustom {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoPageHelper mongoPageHelper;

    @Override
    public List<User> findByCondition(UserQueryRo ro) {
        Criteria criteria = generateCriteria(ro);
        return mongoTemplate.find(Query.query(criteria), User.class);
    }

    @Override
    public Page<User> pageByCondition(UserQueryRo ro) {
        Pageable pageable = ro.getPageable();
        Criteria criteria = generateCriteria(ro);
        return mongoPageHelper.page(criteria, pageable, User.class);
    }

    private Criteria generateCriteria(UserQueryRo ro) {
        Criteria criteria = new Criteria();
        if (ro == null) {
            return criteria;
        }
        // todo: 填充user参数

        return criteria;

    }
}
