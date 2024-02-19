package com.agao.login.constant;

/**
 * @author Agao
 * @date 2024/2/13 12:56
 */
public class LoginConstants {

    /**
     * token过期提醒header
     */
    public static final String TOKEN_EXPIRE_REMIND_HEADER = "xx-system-token-expire-at";

    public static final String AUTH_ERROR_RESPONSE_HEADER = "xx-system-authentication-error";
    /**
     * 请求刷新token的header
     */
    public static final String REFRESH_TOKEN_HEADER_REQ = "xx-system-refresh-token-req";
    /**
     * 响应刷新token的header
     */
    public static final String REFRESH_TOKEN_HEADER_RESP = "xx-system-refresh-token-resp";

    /**
     * 自动下线的header
     */
    public static final String AUTO_LOGOUT_HEADER_REQ = "xx-system-auto-logout-req";


    /**
     * 记住我的参数
     */
    public static final String REMEMBER_ME_PARAMETER = "remember-me";
}
