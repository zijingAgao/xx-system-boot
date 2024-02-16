package com.agao.exception;

import lombok.Getter;

/**
 * 公共异常类
 *
 * @author Agao
 * @date 2024/2/10 14:37
 */
@Getter
public class CommonException extends RuntimeException {

    private Integer code;
    private String msg;

    public CommonException(String message) {
        super(message);
        this.msg = message;
    }

    public CommonException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public CommonException(IExceptionEnum iExceptionEnum) {
        super(iExceptionEnum.getCode() + "-" + iExceptionEnum.getMsg());
        this.code = iExceptionEnum.getCode();
        this.msg = iExceptionEnum.getMsg();
    }
}
