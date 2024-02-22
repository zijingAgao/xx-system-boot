package com.agao.service;

import com.agao.security.enums.UserRole;
import com.agao.user.ro.UserUpdateRo;
import com.agao.user.service.UserService;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Agao
 * @date 2024/2/6 17:32
 */
@SpringBootTest
public class LoginTest {
    @Autowired
    private UserService userService;
    public static final PasswordEncoder PWD_ENCODER = new BCryptPasswordEncoder();

    @Test
    void addUser() {
        UserUpdateRo ro = new UserUpdateRo();

        ro.setUsername("agao@gmail.com");
        ro.setNickName("agao");
        ro.setMobile("17399889988");
        ro.setAutoPwd(true);
//        ro.setResetPwd(false);
//        ro.setPassword();
        ro.setRoles(Lists.newArrayList(UserRole.ADMIN));
        ro.setEnabled(true);
        userService.add(ro);
    }
}
