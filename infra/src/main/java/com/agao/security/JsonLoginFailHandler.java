package com.agao.security;

import com.agao.common.CommonResp;
import com.agao.exception.user.UserExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 * 返回json数据给前端处理
 *
 * @author Agao
 * @date 2024/2/6 10:01
 */
@Slf4j
@Component
public class JsonLoginFailHandler implements AuthenticationFailureHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
        log.debug("user:{} login fail, reason: {} ", req.getParameter("username"), e.getMessage());
        CommonResp<?> commonResp = resolveException(e);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(objectMapper.writeValueAsString(commonResp));
        resp.getWriter().flush();
    }

    private CommonResp<?> resolveException(AuthenticationException e) {
        if (e instanceof DisabledException) {
            return CommonResp.error(UserExceptionCode.USER_DISABLE.getCode(), UserExceptionCode.USER_DISABLE.getMsg());
        }
        return CommonResp.error(UserExceptionCode.BAD_CREDENTIALS.getCode(), UserExceptionCode.BAD_CREDENTIALS.getMsg());
    }
}
