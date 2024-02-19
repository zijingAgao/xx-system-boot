package com.agao.user.repo;

import com.agao.security.enums.UserRole;
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
import org.springframework.util.CollectionUtils;
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
        String nickName = ro.getNickName();
        String mobile = ro.getMobile();
        List<UserRole> roles = ro.getRoles();
        Boolean enabled = ro.getEnabled();

        if (StringUtils.hasText(username)) {
            Pattern pattern = Pattern.compile(Pattern.quote(username), Pattern.CASE_INSENSITIVE);
            criteria.and("username").regex(pattern);
        }
        if (StringUtils.hasText(nickName)) {
            Pattern pattern = Pattern.compile(Pattern.quote(nickName), Pattern.CASE_INSENSITIVE);
            criteria.and("nickName").regex(pattern);
        }
        if (StringUtils.hasText(mobile)) {
            Pattern pattern = Pattern.compile(Pattern.quote(mobile), Pattern.CASE_INSENSITIVE);
            criteria.and("mobile").regex(pattern);
        }
        if (!CollectionUtils.isEmpty(roles)) {
            criteria.and("roles").in(roles);
        }
        if (enabled != null) {
            criteria.and("enabled").is(enabled);
        }

        // todo: 填充user参数

        return criteria;

    }
}
