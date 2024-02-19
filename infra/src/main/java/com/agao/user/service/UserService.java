package com.agao.user.service;

import com.agao.user.entity.User;
import com.agao.exception.user.UserException;
import com.agao.exception.user.UserExceptionCode;
import com.agao.user.repo.UserRepository;
import com.agao.user.ro.UserQueryRo;
import com.agao.user.ro.UserUpdateRo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author Agao
 * @date 2024/2/10 23:22
 */
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordService passwordService;

    public User findOne(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public Page<User> page(UserQueryRo ro) {
        return userRepository.pageByCondition(ro);
    }

    public void add(UserUpdateRo ro) {
        validateUser(ro, true);
        boolean autoPwd = ro.isAutoPwd();
        String password = ro.getPassword();

        User entity = new User();
        BeanUtils.copyProperties(ro, entity);

        String ciphertextPwd;
        if (autoPwd) {
            ciphertextPwd = passwordService.generateEncodeRandomPwd();
        } else {
            ciphertextPwd = PasswordService.PASSWORD_ENCODER.encode(password);
        }
        entity.setPassword(ciphertextPwd);
        userRepository.save(entity);
    }

    public void update(UserUpdateRo ro) {
        validateUser(ro, false);
        String id = ro.getId();
        boolean resetPwd = ro.isResetPwd();
        boolean autoPwd = ro.isAutoPwd();
        User entity = userRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new UserException(UserExceptionCode.USER_NOT_EXIST);
        }
        // 更新变更信息
        BeanUtils.copyProperties(ro, entity);

        // 重置密码
        if (resetPwd) {
            String ciphertextPwd;
            if (autoPwd) {
                ciphertextPwd = passwordService.generateEncodeRandomPwd();
            } else {
                ciphertextPwd = PasswordService.PASSWORD_ENCODER.encode(ro.getPassword());
            }
            entity.setPassword(ciphertextPwd);
        }

        userRepository.save(entity);

        // todo: 保存用户后，发送邮件
        if (resetPwd && autoPwd) {
            // 发送邮件
            log.info("模拟需要发送邮件");
        }
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    public void switchEnable(String id, boolean b) {
        userRepository.findById(id).ifPresent(entity -> {
            entity.setEnabled(b);
            userRepository.save(entity);
        });
    }

    void validateUser(UserUpdateRo ro, boolean add) {
        String username = ro.getUsername();
        boolean autoPwd = ro.isAutoPwd();
        boolean resetPwd = ro.isResetPwd();
        // ----------------------------新增user校验-------------------------------------
        if (add) {
            if (userRepository.countByUsername(username) > 0) {
                throw new UserException(UserExceptionCode.USER_EXIST);
            }
            if (!autoPwd) {
                // 校验密码强度
                passwordService.validatePwdStrength(ro.getPassword());
            }
            return;
        }

        // ----------------------------编辑user校验-------------------------------------
        if (resetPwd) {
            // 校验密码强度
            if (!autoPwd) {
                // 校验密码强度
                passwordService.validatePwdStrength(ro.getPassword());
            }
        }


    }
}
