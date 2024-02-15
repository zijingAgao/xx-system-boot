package com.agao.user.service;

import com.agao.user.entity.User;
import com.agao.exception.user.UserException;
import com.agao.exception.user.UserExceptionCode;
import com.agao.user.repo.UserRepository;
import com.agao.user.ro.UserQueryAbstract;
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

    public User findOne(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public Page<User> page(UserQueryAbstract ro) {
        return userRepository.pageByCondition(ro);
    }

    public void add(UserUpdateRo ro) {
        User entity = new User();
        BeanUtils.copyProperties(ro, entity);
        userRepository.save(entity);
    }

    public void update(UserUpdateRo ro) {
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
}
