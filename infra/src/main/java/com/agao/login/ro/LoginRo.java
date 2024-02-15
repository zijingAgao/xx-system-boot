package com.agao.login.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Agao
 * @date 2024/2/8 22:37
 */
@Data
public class LoginRo {
    @ApiModelProperty(value = "账号-邮箱")
    @Email(message = "邮箱格式错误")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String captcha;
}
