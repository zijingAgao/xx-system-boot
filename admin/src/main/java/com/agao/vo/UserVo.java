package com.agao.vo;

import com.agao.security.enums.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Agao
 * @date 2024/2/10 23:22
 */
@Data
public class UserVo {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "账号,邮箱")
    private String username;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "角色")
    private List<UserRole> roles = new ArrayList<>();
    @ApiModelProperty(value = "是否启用")
    private boolean enabled = true;
}
