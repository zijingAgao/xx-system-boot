package com.agao.common;

import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.*;

/**
 * @author Agao
 * @date 2024/2/5 16:39
 */
@Getter
@Setter
public abstract class BaseEntity {
    @JsonIgnore
    @CreatedDate
    private Long createdTime;
    @JsonIgnore
    @CreatedBy
    private String createdUsername;
    @JsonIgnore
    @LastModifiedDate
    private Long modifiedTime;
    @JsonIgnore
    @LastModifiedBy
    private String modifiedUsername;
    @JsonIgnore
    @Version
    private Long version;
}
