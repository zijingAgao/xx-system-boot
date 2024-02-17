package com.agao.security.jwt;

/**
 * @author Agao
 * @date 2024/2/13 14:09
 */
public class JwtConstants {
    /**
     * token加密key盐值
     */
    public static final String LOGIN_TOKEN_CODEC_SECRET_KEY = "XxSystem";

    /**
     * 会话有效期--即token过期时间 单位：分钟
     */
    public static final Long LOGIN_SESSION_EXPIRE = 10L;
}
