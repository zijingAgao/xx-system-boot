package com.agao.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 处理密码相关业务
 *
 * @author Agao
 * @date 2024/2/15 21:39
 */
@Service
public class PasswordService {
    /**
     * 密码加密器
     */
    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();




}
