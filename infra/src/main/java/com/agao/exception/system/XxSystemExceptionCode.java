package com.agao.exception.system;

import com.agao.exception.IExceptionEnum;
import lombok.AllArgsConstructor;

/**
 * @author Agao
 * @date 2024/2/11 22:05
 */
@AllArgsConstructor
public enum XxSystemExceptionCode implements IExceptionEnum {

    LOGIN_EXPIRE(401, "登录已过期"),
    REPEAT_LOGIN(402, "已在其他地方登录"),

    ;

    private Integer code;
    private String msg;

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return this.getMsg();
    }

    @Override
    public void setMsg(String message) {
        this.msg = message;
    }
    }
