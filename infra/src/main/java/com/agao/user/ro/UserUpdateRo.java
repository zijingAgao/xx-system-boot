package com.agao.user.ro;

import com.agao.security.enums.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Agao
 * @date 2024/2/11 0:17
 */
@Data
public class UserUpdateRo {

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "用户名/邮箱")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "是否自动生成密码")
    private boolean autoPwd = true;

    @ApiModelProperty(value = "是否重置密码")
    private boolean resetPwd = false;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "角色")
    private List<UserRole> roles = new ArrayList<>();

    @ApiModelProperty(value = "是否启用")
    private boolean enabled = true;
}
