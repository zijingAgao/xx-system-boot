package com.agao.init;

import com.agao.user.entity.User;
import com.agao.user.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 初始化系统管理员
 *
 * @author Agao
 * @date 2024/2/14 13:44
 */
@Slf4j
@Component
public class XxSystemInit implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;
    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() == 0) {
            log.info("start init default xx-system admin user");
            User user = new User();
            user.setUsername("admin@gmail.com");
            user.setMobile("13800000000");
            user.setRoles(Lists.newArrayList("ADMIN"));
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode("@ppEx2024"));
            userRepository.save(user);
            log.info("finish init default xx-system admin user");
        }

    }
}
