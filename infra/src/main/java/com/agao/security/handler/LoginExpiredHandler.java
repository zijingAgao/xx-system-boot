package com.agao.security.handler;

import com.agao.common.CommonResp;
import com.agao.exception.IExceptionEnum;
import com.agao.exception.user.UserExceptionCode;
import com.agao.login.constant.LoginConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * 登录过期的处理
 *
 * @author Agao
 * @date 2024/2/14 21:28
 */
public class LoginExpiredHandler implements AuthenticationEntryPoint {
    private final BearerTokenAuthenticationEntryPoint bearerTokenAuthenticationEntryPoint = new BearerTokenAuthenticationEntryPoint();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        bearerTokenAuthenticationEntryPoint.commence(request, response, authException);
        if (authException.getCause() instanceof JwtValidationException) {
            Collection<OAuth2Error> errors = ((JwtValidationException) authException.getCause()).getErrors();
            if (!CollectionUtils.isEmpty(errors)) {
                response.setContentType("application/json;charset=UTF-8");
                ObjectMapper objectMapper = new ObjectMapper();
                OAuth2Error error = errors.stream().findFirst().orElse(null);
                if (error == null || error.getErrorCode().equals("invalid_token")) {
                    error = convert(UserExceptionCode.INVALID_TOKEN);
                }

                response.setHeader(LoginConstants.AUTH_ERROR_RESPONSE_HEADER, error.getErrorCode());
                CommonResp<Object> resp = new CommonResp<>(Integer.valueOf(error.getErrorCode()), error.getDescription());
                response.getWriter().write(objectMapper.writeValueAsString(resp));
            }
        }
    }

    private OAuth2Error convert(IExceptionEnum e) {
        // 默认值，token过期之后返回的OAuth2Error。errorCode 是个字符串 “invalid_token”
        return new OAuth2Error(e.getCode().toString(), e.getMsg(), null);
    }
}
