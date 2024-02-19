package com.agao.user.ro;

import com.agao.common.AbstractPage;
import com.agao.security.enums.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Agao
 * @date 2024/2/10 23:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryRo extends AbstractPage {
    @ApiModelProperty(value = "邮箱")
    private String username;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "角色")
    private List<UserRole> roles;
    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;
}
