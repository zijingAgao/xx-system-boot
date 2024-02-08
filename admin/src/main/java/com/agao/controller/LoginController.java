package com.agao.controller;

import com.agao.ro.LoginRo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Agao
 * @date 2024/2/8 22:36
 */
@Api(tags = "登录接口", value = "登录")
@RestController
@RequestMapping("/api")
public class LoginController {

    @ApiOperation(value = "表单登录")
    @PostMapping("/login")
    public String login(@RequestBody @Validated LoginRo loginRo) {
        return loginRo.getUsername();
    }

    @ApiOperation(value = "获取登录验证码")
    @GetMapping("/login/captcha")
    public String captcha() {
        return "captcha";
    }
}
