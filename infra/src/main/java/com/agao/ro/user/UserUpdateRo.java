package com.agao.ro.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Agao
 * @date 2024/2/11 0:17
 */
@Data
public class UserUpdateRo {

    private String id;
    /**
     * 账号
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
}
