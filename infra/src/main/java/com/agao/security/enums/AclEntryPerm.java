package com.agao.security.enums;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * @author Agao
 * @date 2024/2/6 16:43
 */
@Getter
public enum AclEntryPerm {
    USER_VIEW("用户查看权限"),
    USER_MGT("用户管理权限", USER_VIEW),
    /**
     * 最低权限要求，认证即可访问
     */
    AUTHED("已认证"),
    ;

    /**
     * 说明
     */
    private final String desc;
    /**
     * 子权限 eg：mgt包含view 支持子一级
     */
    private final AclEntryPerm[] subPerms;

    AclEntryPerm(String desc) {
        this(desc, new AclEntryPerm[]{});
    }

    AclEntryPerm(String desc, AclEntryPerm... subPerms) {
        this.desc = desc;
        this.subPerms = subPerms;
    }

    public static List<AclEntryPerm> getAllPerms() {
        return Lists.newArrayList(values());
    }

    /**
     * 获取当前枚举的权限，包含子权限
     *
     * @return
     */
    public List<AclEntryPerm> getCurrentPerms() {
        if (subPerms == null || subPerms.length == 0) {
            return Lists.newArrayList(this);
        }
        List<AclEntryPerm> perms = Lists.newArrayList(subPerms);
        perms.add(this);
        return perms;
    }
}
