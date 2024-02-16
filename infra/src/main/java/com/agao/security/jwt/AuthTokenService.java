package com.agao.security.jwt;

import com.agao.user.entity.User;
import com.agao.security.userdetails.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * token管理器 签发token
 *
 * @author Agao
 * @date 2024/2/13 13:14
 */
@Slf4j
@Component
public class AuthTokenService {

    @Autowired
    private JwtCodec jwtCodec;
    /**
     * 记住我 过期时间 即刷新token的过期时间
     */
    @Value("${xx-system.remember-me.expire:7d}")
    private Duration rememberMeExpire;

    public AuthToken authenticate(User user, boolean rememberMe) {
        if (user == null) {
            return null;
        }
        AuthUser authUser = AuthUser.convertForm(user, rememberMe, UUID.randomUUID().toString());
        // 生成认证token
        String accessToken = jwtCodec.encodeForAccessToken(authUser, getAccessTokenExpireSeconds(), TimeUnit.MINUTES);
        // 生成刷新token
        String refreshToken = jwtCodec.encodeForRefreshToken(authUser, getRefreshTokenExpireSeconds(rememberMe), TimeUnit.MINUTES);
        return new AuthToken(accessToken, refreshToken, getAccessTokenExpireSeconds(), authUser);
    }

    public AuthToken refresh(String refreshToken) {

        return null;

    }

    /**
     * 获取刷新token过期时间
     *
     * @param rememberMe 记住我？
     * @return
     */
    private long getRefreshTokenExpireSeconds(boolean rememberMe) {
        if (rememberMe) {
            return rememberMeExpire.getSeconds();
        }
        return getAccessTokenExpireSeconds();
    }

    /**
     * 获取认证token过期时间
     *
     * @return
     */
    private long getAccessTokenExpireSeconds() {
        return JwtConstants.LOGIN_SESSION_EXPIRE;
    }

}
