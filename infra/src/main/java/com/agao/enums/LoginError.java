package com.agao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Agao
 * @date 2024/2/6 11:12
 */
@Getter
@AllArgsConstructor
public enum LoginError {
    BAD_CREDENTIALS(100, "账号或密码错误"),
    USER_DISABLE(101, "用户被锁定"),
    EMPTY_VALIDATE_CODE(102, "验证码为空"),
    INVALID_VALIDATE_CODE(103, "验证码错误");

    private final Integer code;
    private final String msg;
}
