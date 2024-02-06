package com.agao.security.userdetails;

import com.agao.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;

/**
 * @author Agao
 * @date 2024/2/6 11:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser implements UserDetails {
    private String username;
    private String password;
    private List<String> roles;
    private Set<GrantedAuthority> authorities;
    private boolean enabled;

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
}
