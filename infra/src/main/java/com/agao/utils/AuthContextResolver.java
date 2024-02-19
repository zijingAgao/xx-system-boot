package com.agao.utils;

import com.agao.exception.user.UserException;
import com.agao.security.userdetails.AuthUser;
import com.agao.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

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
        if (principal instanceof AuthUser) {
            AuthUser authUser = (AuthUser) principal;
            return User.convertForm(authUser);
        }
        if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            return User.convertForm(jwt);
        }
        return null;
    }
}
