package com.agao.user.repo;

import com.agao.user.entity.User;
import com.agao.user.ro.UserQueryRo;
import com.agao.utils.MongoPageHelper;
import org.hibernate.criterion.CriteriaQuery;
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
    public List<User> findByCondition(UserQueryRo ro) {
        Criteria criteria = generateCriteria(ro);
        return mongoTemplate.find(Query.query(criteria), User.class);
    }

    @Override
    public Page<User> pageByCondition(UserQueryRo ro) {
        Pageable pageable = ro.obtainPageable();
        Criteria criteria = generateCriteria(ro);
        return mongoPageHelper.page(criteria, pageable, User.class);
    }

    private Criteria generateCriteria(UserQueryRo ro) {
        Criteria criteria = new Criteria();
        if (ro == null) {
            return criteria;
        }
        String username = ro.getUsername();

        if (StringUtils.hasText(username)) {
            Pattern pattern = Pattern.compile(Pattern.quote(username), Pattern.CASE_INSENSITIVE);
            criteria.and("username").regex(pattern);
        }

        // todo: 填充user参数

        return criteria;

    }
}
