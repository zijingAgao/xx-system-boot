package com.agao.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;

/**
 * @author Agao
 * @date 2024/2/5 16:39
 */
@Getter
@Setter
public abstract class BaseAuditEntity {
    @CreatedDate
    private Long createdTime;
    @CreatedBy
    private String createdUsername;
    @LastModifiedDate
    private Long modifiedTime;
    @LastModifiedBy
    private String modifiedUsername;
    @Version
    private Long version;
}
