package com.agao.setting.entity;

import com.agao.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 配置项
 * @author Agao
 * @date 2024/2/17 21:00
 */
@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class Setting extends BaseEntity {
    @Id
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "key")
    private String key;
    @ApiModelProperty(value = "value")
    private String value;
    @ApiModelProperty(value = "备注")
    private String remark;
}
