package com.agao.security;

import com.agao.common.CommonResp;
import com.agao.security.userdetails.AuthUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功之后的处理
 * 即账号密码都正确之后的业务
 * eg：密码有效期，是否开了双因子认证
 *
 * @author Agao
 * @date 2024/2/6 11:19
 */
@Component
public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
        AuthUser authUser = (AuthUser) auth.getPrincipal();
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(objectMapper.writeValueAsString(CommonResp.success(authUser)));
        resp.getWriter().flush();
    }
}
