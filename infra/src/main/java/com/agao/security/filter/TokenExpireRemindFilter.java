package com.agao.security.filter;

import com.agao.constant.LoginConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * token 令牌过期提醒
 *
 * @author Agao
 * @date 2024/2/13 12:41
 */
@Slf4j
public class TokenExpireRemindFilter extends OncePerRequestFilter {
    /**
     * token 过期提醒时间 过期前5分钟
     */
    private final Duration REMIND_DURATION = Duration.of(5, ChronoUnit.MINUTES);
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object o = authentication.getPrincipal();
            if (o instanceof Jwt) {
                Jwt jwt = (Jwt) o;
                Instant expiresAt = jwt.getExpiresAt();
                if (expiresAt == null) {
                    throw new RuntimeException("jwt token is null");
                }
                if (expiresAt.minus(REMIND_DURATION).isBefore(Instant.now())) {
                    response.setHeader(LoginConstants.TOKEN_EXPIRE_REMIND_HEADER, String.valueOf(System.currentTimeMillis()));
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
