package com.agao.controller;

import com.agao.common.CommonResp;
import com.agao.user.entity.User;
import com.agao.user.ro.UserQueryRo;
import com.agao.user.ro.UserUpdateRo;
import com.agao.user.service.UserService;
import com.agao.utils.AuthContextResolver;
import com.agao.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Agao
 * @date 2024/2/10 23:19
 */
@Api(tags = "用户接口")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页查询用户")
    @GetMapping("/api/user")
    public CommonResp<List<UserVo>> page(UserQueryRo ro) {
        Page<User> page = userService.page(ro);
        long total = page.getTotalElements();
        if (total == 0) {
            return CommonResp.success(new ArrayList<>(), page);
        }
        // 转vo
        List<UserVo> vos = new ArrayList<>();
        for (User user : page.getContent()) {
            UserVo vo = new UserVo();
            BeanUtils.copyProperties(user, vo);
            vos.add(vo);
        }
        return CommonResp.success(vos, page);
    }

    @ApiOperation(value = "查询用户详情")
    @GetMapping("/api/user/{id}")
    public CommonResp<UserVo> findOne(@PathVariable String id) {
        User user = userService.findOne(id);
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user, vo);
        return CommonResp.success(vo);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/api/user")
    public void save(@RequestBody @Validated UserUpdateRo ro) {
        userService.add(ro);
    }

    @ApiOperation(value = "修改用户")
    @PutMapping("/api/user")
    public void update(@RequestBody @Validated UserUpdateRo ro) {
        userService.update(ro);
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/api/user/{id}")
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }

    @ApiOperation(value = "禁用用户")
    @PutMapping("/api/user/disable/{id}")
    public void disable(@PathVariable String id) {
        userService.switchEnable(id, false);
    }

    @ApiOperation(value = "启用用户")
    @PutMapping("/api/user/enable/{id}")
    public void enable(@PathVariable String id) {
        userService.switchEnable(id, true);
    }

    @ApiOperation(value = "获取当前登录的用户信息")
    @GetMapping("/api/user/current")
    public CommonResp<UserVo> current() {
        User user = AuthContextResolver.getCurrentUserException();
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user, vo);
        return CommonResp.success(vo);
    }

}
