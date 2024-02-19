package com.agao.utils;

import com.agao.exception.user.UserException;
import com.agao.security.enums.UserRole;
import com.agao.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Agao
 * @date 2024/2/19 16:58
 */
public class AuthContextResolver {

    public static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        return getUserFromAuthentication(auth);
    }

    public static User getCurrentUserException() {
        User user = getCurrentUser();
        if (user == null) {
            throw new UserException("当前用户未登录");
        }
        return user;
    }

    public static User getUserFromAuthentication(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        } else if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            User user = new User();
            user.setId(jwt.getClaimAsString("id"));
            user.setUsername(jwt.getClaimAsString("username"));
            List<String> roleList = jwt.getClaimAsStringList("roles");
            user.setRoles(CollectionUtils.isEmpty(roleList) ? new ArrayList<>() : roleList.stream().map(UserRole::valueOf).collect(toList()));
            user.setEnabled(jwt.getClaimAsBoolean("enabled"));
            return user;
        }
        return null;
    }
}
