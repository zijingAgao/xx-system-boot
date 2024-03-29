package com.agao.exception.user;

import com.agao.exception.IExceptionEnum;
import lombok.AllArgsConstructor;

/**
 * @author Agao
 * @date 2024/2/10 23:06
 */
@AllArgsConstructor
public enum UserExceptionCode implements IExceptionEnum {

    USER_NOT_EXIST(1001, "用户不存在"),
    USER_EXIST(1002, "用户已存在"),
    USER_PASSWORD_ERROR(1003, "密码错误"),
    USER_PASSWORD_EMPTY(1004, "密码不能为空"),
    USER_PASSWORD_LENGTH_ERROR(1005, "密码长度必须大于6位"),
    USER_PASSWORD_NOT_MATCH(1006, "两次密码不一致"),

    BAD_CREDENTIALS(1007, "账号或密码错误"),
    USER_DISABLE(1008, "用户被锁定"),
    EMPTY_VALIDATE_CODE(1009, "验证码为空"),
    INVALID_VALIDATE_CODE(1010, "验证码错误"),
    REFRESH_TOKEN_EMPTY(1011,"刷新token为空"),
    INVALID_TOKEN(1012,"无效的认证token"),
    INVALID_REFRESH_TOKEN(1013,"无效的刷新token"),
    USER_PASSWORD_STRENGTH_NOT_MATCH(1014, "密码强度不符合要求"),
    SESSION_OUT(1015, "会话已过期"),
    REPEAT_LOGIN(1016, "已在其他地方登录"),
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
        return this.msg;
    }

    @Override
    public void setMsg(String message) {
        this.msg = message;
    }
}
