package com.agao.user.repo;

import com.agao.user.entity.User;
import com.agao.user.ro.UserQueryRo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Agao
 * @date 2024/2/10 23:48
 */
public interface UserRepositoryCustom {

    /**
     * ro 条件查询 不分页
     *
     * @param ro
     * @return
     */
    List<User> findByCondition(UserQueryRo ro);

    /**
     * ro 条件查询分页
     *
     * @param ro
     * @return
     */
    Page<User> pageByCondition(UserQueryRo ro);
}
