package com.agao.security;

import com.agao.common.CommonResp;
import com.agao.exception.system.XxSystemExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Agao
 * @date 2024/2/11 21:59
 */
@Slf4j
@Component
public class JsonSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();
        CommonResp<?> resp = CommonResp.error(XxSystemExceptionCode.LOGIN_EXPIRE.getCode(), XxSystemExceptionCode.LOGIN_EXPIRE.getMsg());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(resp));
        response.getWriter().flush();
    }
}
