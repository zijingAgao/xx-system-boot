package com.agao.entity.user;

import com.agao.entity.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Agao
 * @date 2024/2/5 16:15
 */
@Data
@Document
@EqualsAndHashCode(callSuper = true)
public class User extends BaseAuditEntity {
    @Id
    private String id;

    private String name;

    private String phone;

    private String pwd;
}
