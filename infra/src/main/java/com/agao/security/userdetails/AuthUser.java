package com.agao.security.userdetails;

import com.agao.security.enums.UserRole;
import com.agao.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * @author Agao
 * @date 2024/2/6 11:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser implements UserDetails {
    private String id;
    private String username;
    private String password;
    private List<UserRole> roles;
    private Set<GrantedAuthority> authorities;
    private boolean enabled;
    private String sessionId;
    private boolean rememberMe;

    public AuthUser(User user, Set<GrantedAuthority> grantedAuthorities) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.authorities = grantedAuthorities;
        this.enabled = user.isEnabled();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    public static AuthUser convertForm(User user, boolean rememberMe, String sessionId) {
        AuthUser authUser = new AuthUser();
        BeanUtils.copyProperties(user, authUser);
        authUser.setRememberMe(rememberMe);
        authUser.setSessionId(sessionId);
        return authUser;
    }

    public static AuthUser from(Jwt jwt) {
        AuthUser authUser = new AuthUser();
        authUser.id = jwt.getClaimAsString("id");
        authUser.username = jwt.getClaimAsString("username");
        List<String> roleList = jwt.getClaimAsStringList("roles");
        authUser.roles = (CollectionUtils.isEmpty(roleList) ? new ArrayList<>() : roleList.stream().map(UserRole::valueOf).collect(toList()));
        Boolean rememberMeObj = jwt.getClaimAsBoolean("rememberMe");
        authUser.rememberMe = rememberMeObj != null && rememberMeObj;
        authUser.sessionId = jwt.getClaimAsString("sessionId");
        authUser.enabled = jwt.getClaimAsBoolean("enabled");

        return authUser;
    }
}
