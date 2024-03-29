package com.agao.security.handler;

import com.agao.common.CommonResp;
import com.agao.login.constant.LoginConstants;
import com.agao.security.LoginData;
import com.agao.security.cache.LoginExpiredCache;
import com.agao.security.jwt.AuthToken;
import com.agao.security.jwt.AuthTokenService;
import com.agao.security.jwt.JwtCodec;
import com.agao.security.userdetails.AuthUser;
import com.agao.setting.SettingConst;
import com.agao.setting.cache.SettingCache;
import com.agao.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功之后的处理
 * 即账号密码都正确之后的业务
 * eg：密码有效期，是否开了双因子认证
 *
 * @author Agao
 * @date 2024/2/6 11:19
 */
@Component
public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthTokenService authTokenService;
    @Autowired
    private LoginExpiredCache loginExpiredCache;
    @Autowired
    private JwtCodec jwtCodec;
    @Autowired
    private SettingCache settingCache;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
        AuthUser authUser = (AuthUser) auth.getPrincipal();
        User user = User.convertForm(authUser);
        LoginData loginData = checkLogin(user, req, resp);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(objectMapper.writeValueAsString(CommonResp.success(loginData)));
        resp.getWriter().flush();
    }

    public LoginData checkLogin(User user, HttpServletRequest req, HttpServletResponse resp) {
        boolean rememberMe = rememberMe(req);
        // 生成token
        AuthToken authToken = authTokenService.authenticate(user, rememberMe);
        // 是否开启单点登录
        if (enableOneSessionLogin()) {
            Jwt jwt = jwtCodec.decode(authToken.getRefreshToken());
            loginExpiredCache.logoutUserForLogin(user.getId(), jwt.getIssuedAt().toEpochMilli(), jwt.getExpiresAt().toEpochMilli());
        }

        return LoginData.builder()
                .email(user.getUsername())
                .accessToken(authToken.getAccessToken())
                .refreshToken(authToken.getRefreshToken())
                .accessTokenExpire(authToken.getAccessTokenExpire())
                .build();
    }

    /**
     * 是否需要记住我
     *
     * @param request
     * @return
     */
    public boolean rememberMe(HttpServletRequest request) {
        String value = request.getParameter(LoginConstants.REMEMBER_ME_PARAMETER);
        if (!StringUtils.hasText(value)) {
            return false;
        }
        return value.equalsIgnoreCase("true")
                || value.equalsIgnoreCase("on")
                || value.equalsIgnoreCase("yes")
                || value.equals("1");
    }

    /**
     * 是否开启单点登录
     *
     * @return
     */
    private boolean enableOneSessionLogin() {
        return settingCache.getConfigValueAsBoolean(
                SettingConst.CFG_KEY_AUTH_SINGLE_LOGIN,
                SettingConst.CFG_DEFAULT_AUTH_SINGLE_LOGIN);
    }
}
