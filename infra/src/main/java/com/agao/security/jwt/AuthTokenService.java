package com.agao.security.jwt;

import com.agao.exception.user.UserException;
import com.agao.exception.user.UserExceptionCode;
import com.agao.security.userdetails.AuthUser;
import com.agao.setting.SettingConst;
import com.agao.setting.cache.SettingCache;
import com.agao.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
    @Autowired
    private SettingCache settingCache;

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
        if (!StringUtils.hasText(refreshToken)) {
            throw new UserException(UserExceptionCode.REFRESH_TOKEN_EMPTY);
        }
        AuthUser authUser;
        try {
            Jwt jwt = jwtCodec.decode(refreshToken);
            authUser = AuthUser.from(jwt);
        } catch (JwtException e) {
            throw new UserException(UserExceptionCode.INVALID_REFRESH_TOKEN);
        }
        // 生成认证token
        String newAccessToken = jwtCodec.encodeForAccessToken(authUser, getAccessTokenExpireSeconds(), TimeUnit.MINUTES);
        // 生成刷新token
        String newRefreshToken = jwtCodec.encodeForRefreshToken(authUser, getRefreshTokenExpireSeconds(authUser.isRememberMe()), TimeUnit.MINUTES);

        AuthToken authToken = new AuthToken();
        authToken.setAccessToken(newAccessToken);
        authToken.setRefreshToken(newRefreshToken);
        authToken.setAccessTokenExpire(getAccessTokenExpireSeconds());
        authToken.setAuthUser(authUser);
        return authToken;
    }

    /**
     * 获取刷新token过期时间
     *
     * @param rememberMe 记住我？
     * @return
     */
    private long getRefreshTokenExpireSeconds(boolean rememberMe) {
        if (rememberMe) {
            int expire = settingCache.getConfigValueAsInt(SettingConst.CFG_KEY_AUTH_REFRESH_TOKEN_EXPIRE, SettingConst.CFG_DEFAULT_AUTH_REFRESH_TOKEN_EXPIRE);
            return Duration.ofDays(expire).getSeconds();
        }
        return getAccessTokenExpireSeconds();
    }

    /**
     * 获取认证token过期时间
     *
     * @return
     */
    private long getAccessTokenExpireSeconds() {
        return settingCache.getConfigValueAsLong(SettingConst.CFG_KEY_AUTH_SESSION_EXPIRE, SettingConst.CFG_DEFAULT_SYSTEM_SESSION_EXPIRE);
    }
}
