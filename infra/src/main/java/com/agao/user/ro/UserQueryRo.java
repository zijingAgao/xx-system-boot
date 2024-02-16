package com.agao.user.ro;

import com.agao.common.AbstractPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Agao
 * @date 2024/2/10 23:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryRo extends AbstractPage {
    @ApiModelProperty(value = "邮箱")
    private String username;
}
