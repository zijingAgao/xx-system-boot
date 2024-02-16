package com.agao.security.jwt;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Agao
 * @date 2024/2/14 21:32
 */
public class CustomGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private String authoritiesClaimName = "scope";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : getAuthorities(jwt)) {
            GrantedAuthority grantedAuthority = null;
            if (authority.endsWith(JwtCodec.PARTIAL_GRANTED_AUTHORITY_SUFFIX)) {
                grantedAuthority = new PartialGrantedAuthority(authority.replace(JwtCodec.PARTIAL_GRANTED_AUTHORITY_SUFFIX, ""), "Assignable");
            } else {
                grantedAuthority = new SimpleGrantedAuthority(authority);
            }
            grantedAuthorities.add(grantedAuthority);
        }
        return grantedAuthorities;
    }

    private Collection<String> getAuthorities(Jwt jwt) {
        String claimName = authoritiesClaimName;

        Object authorities = jwt.getClaim(claimName);
        if (authorities instanceof String) {
            if (StringUtils.hasText((String) authorities)) {
                return Arrays.asList(((String) authorities).split(" "));
            }
            return Collections.emptyList();
        } else if (authorities instanceof Collection) {
            return (Collection<String>) authorities;
        }

        return Collections.emptyList();
    }
}
