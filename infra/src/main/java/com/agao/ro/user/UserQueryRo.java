package com.agao.ro.user;

import com.agao.ro.PageRo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Agao
 * @date 2024/2/10 23:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryRo extends PageRo {
    @ApiModelProperty(value = "邮箱")
    private String username;
}
