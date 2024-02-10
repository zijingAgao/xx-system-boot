package com.agao.controller;

import com.agao.common.CommonResp;
import com.agao.common.PageData;
import com.agao.entity.user.User;
import com.agao.ro.user.UserQueryRo;
import com.agao.ro.user.UserUpdateRo;
import com.agao.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    public CommonResp<PageData<User>> page(UserQueryRo ro) {
        Page<User> page = userService.page(ro);
        return CommonResp.success(new PageData<>(page));
    }

    @ApiOperation(value = "查询用户详情")
    @GetMapping("/api/user/{id}")
    public CommonResp<User> findOne(@PathVariable String id) {
        User user = userService.findOne(id);
        return CommonResp.success(user);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/api/user")
    public void save(@RequestBody UserUpdateRo ro) {
        userService.add(ro);
    }

    @ApiOperation(value = "修改用户")
    @PutMapping("/api/user")
    public void update(@RequestBody UserUpdateRo ro) {
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

}
