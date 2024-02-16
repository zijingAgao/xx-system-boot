package com.agao.user.entity;

import com.agao.common.BaseEntity;
import com.agao.security.userdetails.AuthUser;
import com.agao.user.enums.PasswordStrength;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Agao
 * @date 2024/2/5 16:15
 */
@Data
@Document
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    @Id
    private String id;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 账号,邮箱
     */
    private String username;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 角色 一个账号可以对应多个角色
     */
    private List<String> roles = new ArrayList<>();
    /**
     * 是否启用
     */
    private boolean enabled = true;
    /**
     * 密码强度
     */
    private PasswordStrength passwordStrength;

    /**
     * 转换实体
     *
     * @param authUser
     * @return
     */
    public static User convertForm(AuthUser authUser) {
        User user = new User();
        BeanUtils.copyProperties(authUser, user);
        return user;
    }
}
