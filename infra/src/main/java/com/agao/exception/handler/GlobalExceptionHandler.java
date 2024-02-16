package com.agao.exception.handler;

import com.agao.exception.CommonException;
import com.agao.common.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Agao
 * @date 2024/2/10 14:45
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = CommonException.class)
    public CommonResp<?> commonExceptionHandler(CommonException e) {
        return CommonResp.error(e.getCode(), e.getMsg());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public CommonResp<?> ExceptionHandler(Exception e) {
        return CommonResp.error(e.getMessage());
    }


}
