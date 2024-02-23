package com.agao.setting;

/**
 * setting 配置项 常量，以key，defaultValue 成对存在
 *
 * @author Agao
 * @date 2024/2/19 9:26
 */
public class SettingConst {

    /**
     * 用户密码强度
     */
    public static final String CFG_KEY_USER_PASSWORD_STRENGTH = "system.user.high-password-strength";
    public static final Boolean CFG_DEFAULT_USER_PASSWORD_STRENGTH = false;

    /**
     * 会话有效期，token有效时间 单位：分钟
     */
    public static final String CFG_KEY_AUTH_SESSION_EXPIRE = "system.auth.session-expire";
    public static final Integer CFG_DEFAULT_SYSTEM_SESSION_EXPIRE = 5;

    /**
     * token加密key盐值
     */
    public static final String CFG_KEY_AUTH_TOKEN_CODEC_SECRET = "system.auth.token-codec-secret";
    public static final String CFG_DEFAULT_AUTH_TOKEN_CODEC_SECRET = "XxSystem";

    /**
     * 刷新token 存储时间，rememberMe 单位：天
     */
    public static final String CFG_KEY_AUTH_REFRESH_TOKEN_EXPIRE = "system.auth.refresh-token-expire";
    public static final Integer CFG_DEFAULT_AUTH_REFRESH_TOKEN_EXPIRE = 7;

    /**
     * 单点登录开关，一个用户只能在一个设备上登录，再另外一个设备登录会挤掉之前的设备
     */
    public static final String CFG_KEY_AUTH_SINGLE_LOGIN = "system.auth.one-session-login";
    public static final Boolean CFG_DEFAULT_AUTH_SINGLE_LOGIN = true;
}
