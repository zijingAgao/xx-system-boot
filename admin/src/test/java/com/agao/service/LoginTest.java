package com.agao.service;

import com.agao.entity.user.User;
import com.agao.enums.UserRole;
import com.agao.repo.UserRepository;
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
    private UserRepository userRepository;
    public static final PasswordEncoder PWD_ENCODER = new BCryptPasswordEncoder();

    @Test
    void addUser() {
        User user = new User();
        user.setUsername("agao@gmail.com");
        user.setMobile("17399889988");
        user.setPassword(PWD_ENCODER.encode("123456"));
        user.setRoles(Lists.newArrayList(UserRole.ADMIN.getValue()));
        user.setEnabled(true);

        userRepository.save(user);
    }
}
