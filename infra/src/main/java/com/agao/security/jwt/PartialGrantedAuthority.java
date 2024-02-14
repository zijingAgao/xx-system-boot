package com.agao.security.jwt;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Agao
 * @date 2024/2/14 21:34
 */
public class PartialGrantedAuthority implements GrantedAuthority {
    private String authority;
    private String scope;

    public PartialGrantedAuthority(String name, String scope) {
        this.authority = name;
        this.scope = scope;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public String getScope() {
        return scope;
    }
}
