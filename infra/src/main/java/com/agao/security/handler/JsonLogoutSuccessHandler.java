package com.agao.security.handler;

import com.agao.common.CommonResp;
import com.agao.login.constant.LoginConstants;
import com.agao.security.cache.BlackSessionCache;
import com.agao.security.jwt.JwtCodec;
import com.agao.security.userdetails.AuthUser;
import com.agao.user.entity.User;
import com.agao.user.repo.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Agao
 * @date 2024/2/11 21:55
 */
@Slf4j
@Component
public class JsonLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtCodec jwtCodec;
    @Autowired
    private BlackSessionCache blackSessionCache;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {

        String token = "";
        try {
            String autoLogoutHeader = request.getHeader(LoginConstants.AUTO_LOGOUT_HEADER_REQ);
            boolean autoLogout = Boolean.parseBoolean(autoLogoutHeader);

            token = request.getHeader(LoginConstants.REFRESH_TOKEN_HEADER_REQ);
            Jwt jwt = jwtCodec.decode(token);
            AuthUser authUser = AuthUser.from(jwt);
            String sessionId = authUser.getSessionId();
            boolean rememberMe = authUser.isRememberMe();
            String id = authUser.getId();

            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                log.warn("user:{} not exists", authUser);
                return;
            }

            if (!StringUtils.hasLength(sessionId)) {
                log.warn("token:{} with no sessionId", token);
                return;
            }
            // 丢入黑名单session
            blackSessionCache.put(sessionId, user.getId());
            log.info("user : {} logout,email is : {} , put into blackSessionCache, sessionId is : {}", user.getNickName(), user.getUsername(), sessionId);
        } catch (JwtException e) {
            log.warn("invalid token: {}", token);
        } catch (Exception e) {
            log.error("error resolve logout, token: {}", token, e);
        }

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(CommonResp.success()));
        response.getWriter().flush();
    }
}
