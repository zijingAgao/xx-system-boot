package com.agao.security.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author Agao
 * @date 2024/2/6 16:40
 */
@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN("ADMIN", AclEntryPerm.getAllPerms()),
    AUDIT_ADMIN("AUDIT_ADMIN", AclEntryPerm.getAllPerms()),

    USER("USER", Lists.newArrayList())
    ;

    private final String value;
    private final List<AclEntryPerm> aclPerms;

    public static UserRole fromValue(String value) {
        for (UserRole role : UserRole.values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        return null;
    }
}
