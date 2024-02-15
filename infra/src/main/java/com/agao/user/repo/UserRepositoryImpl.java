package com.agao.user.repo;

import com.agao.user.entity.User;
import com.agao.user.ro.UserQueryAbstract;
import com.agao.utils.MongoPageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

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
    public List<User> findByCondition(UserQueryAbstract ro) {
        Criteria criteria = generateCriteria(ro);
        return mongoTemplate.find(Query.query(criteria), User.class);
    }

    @Override
    public Page<User> pageByCondition(UserQueryAbstract ro) {
        Pageable pageable = ro.obtainPageable();
        Criteria criteria = generateCriteria(ro);
        return mongoPageHelper.page(criteria, pageable, User.class);
    }

    private Criteria generateCriteria(UserQueryAbstract ro) {
        Criteria criteria = new Criteria();
        if (ro == null) {
            return criteria;
        }
        String username = ro.getUsername();

        if (StringUtils.hasText(username)) {
            Pattern pattern = Pattern.compile(username, Pattern.CASE_INSENSITIVE);
            criteria.regex(pattern);
        }

        // todo: 填充user参数

        return criteria;

    }
}
