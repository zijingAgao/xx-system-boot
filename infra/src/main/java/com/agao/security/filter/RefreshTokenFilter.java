package com.agao.security.filter;

import com.agao.login.constant.LoginConstants;
import com.agao.security.jwt.AuthToken;
import com.agao.security.jwt.AuthTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Agao
 * @date 2024/2/16 18:28
 */
public class RefreshTokenFilter extends OncePerRequestFilter {
    private final AuthTokenService authTokenService;

    public RefreshTokenFilter(AuthTokenService authTokenService) {
        this.authTokenService = authTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 获取刷新token
        String refreshToken = request.getHeader(LoginConstants.REFRESH_TOKEN_HEADER_REQ);
        if (!StringUtils.hasText(refreshToken)){
            filterChain.doFilter(request,response);
            return;
        }
        // 刷新token
        AuthToken authToken = authTokenService.refresh(refreshToken);
        BearerTokenAuthenticationToken authenticationToken = new BearerTokenAuthenticationToken(authToken.getAccessToken());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        ObjectMapper objectMapper = new ObjectMapper();
        response.setHeader(LoginConstants.REFRESH_TOKEN_HEADER_RESP, objectMapper.writeValueAsString(authToken));
        filterChain.doFilter(request,response);
    }
}
