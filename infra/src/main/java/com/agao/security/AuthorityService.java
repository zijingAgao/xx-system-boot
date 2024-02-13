package com.agao.security;

import com.agao.entity.user.User;
import com.agao.security.enums.AclEntryPerm;
import com.agao.security.enums.UserRole;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Agao
 * @date 2024/2/6 17:00
 */
@Service
public class AuthorityService {

    private final RoleVoter roleVoter = new RoleVoter();
    public Set<GrantedAuthority> getAuthorities(User user) {
        List<String> roles = user.getRoles();

        // 所有的权限点
        List<AclEntryPerm> perms = new ArrayList<>();
        roles.stream()
                .map(UserRole::fromValue)
                .filter(Objects::nonNull)
                .forEach(role-> role.getAclPerms().forEach(acl -> perms.addAll(acl.getCurrentPerms())));

        Set<GrantedAuthority> authorities = perms.stream()
                .map(perm -> new SimpleGrantedAuthority(perm.name()))
                .collect(Collectors.toSet());
        // 当前账户角色
        Set<GrantedAuthority> roleAuthorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(roleVoter.getRolePrefix() + role))
                .collect(Collectors.toSet());

        authorities.addAll(roleAuthorities);
        return authorities;
    }

}
