package com.agao.security.jwt;

import com.agao.login.constant.LoginConstants;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Agao
 * @date 2024/2/14 21:28
 */
public class LoginExpiredAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private BearerTokenAuthenticationEntryPoint bearerTokenAuthenticationEntryPoint = new BearerTokenAuthenticationEntryPoint();
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        bearerTokenAuthenticationEntryPoint.commence(request, response, authException);
        if (authException.getCause() instanceof JwtValidationException) {
            Collection<OAuth2Error> errors =
                    ((JwtValidationException) authException.getCause()).getErrors();
            if (!CollectionUtils.isEmpty(errors)) {
                OAuth2Error error = errors.stream().findFirst().orElse(null);
                response.setHeader(LoginConstants.AUTH_ERROR_RESPONSE_HEADER, error.getErrorCode());
            }
        }
    }
}
