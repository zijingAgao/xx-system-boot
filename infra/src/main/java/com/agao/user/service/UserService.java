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
        validateUser(ro,true);
        User entity = new User();
        BeanUtils.copyProperties(ro, entity);
        String encodePwd = PasswordService.passwordEncoder.encode(ro.getPassword());
        entity.setPassword(encodePwd);
        userRepository.save(entity);
    }

    public void update(UserUpdateRo ro) {
        validateUser(ro,false);
        String id = ro.getId();
        User entity = userRepository.findById(id).orElse(null);
        if (entity == null) {
            log.warn("update user fail, id is {}", id);
            throw new UserException(UserExceptionCode.USER_NOT_EXIST);
        }
        BeanUtils.copyProperties(ro, entity);
        userRepository.save(entity);
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
        if (add) {
            if (userRepository.countByUsername(username) > 0) {
                throw new UserException(UserExceptionCode.USER_EXIST);
            }
        }

        // 校验密码强度


    }
}