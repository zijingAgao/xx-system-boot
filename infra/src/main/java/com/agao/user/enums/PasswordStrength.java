package com.agao.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Agao
 * @date 2024/2/15 21:43
 */
@Getter
@AllArgsConstructor
public enum PasswordStrength {
    UNKNOWN(null),
    /**
     * 所有字符只有要6-16位数即可
     */
    LOW("^[\\x21-\\x7E]{6,16}$"),
    /**
     * 所有字符包含 数字，字母，特殊字符 ~`!@#$%&*()_-+=|\/[]{};':",.<>? 且位数为 12-32
     */
    HIGH("^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[~`!@#$%^&*()_\\-+=|\\\\\\/\\[\\]{};':\",.<>?])[\\da-zA-Z~`!@#$%^&*()_\\-+=|\\\\\\/\\[\\]{};':\",.<>?]{12,32}$"),

    ;

    /**
     * 正则表达式
     */
    private final String regex;

    public boolean matches(String password) {
        if (regex == null || password == null) {
            return false;
        }
        return password.matches(regex);
    }

    public static PasswordStrength getPwdStrength(String password) {
        if (HIGH.matches(password)) {
            return HIGH;
        } else if (LOW.matches(password)) {
            return LOW;
        }
        return UNKNOWN;
    }
}
