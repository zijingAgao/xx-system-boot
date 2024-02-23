package com.agao.security.jwt;

import com.agao.security.jwt.validator.BlackSessionTokenValidator;
import com.agao.security.jwt.validator.TimeoutTokenValidator;
import com.agao.security.userdetails.AuthUser;
import com.agao.setting.SettingConst;
import com.agao.setting.cache.SettingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.io.BaseEncoding;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * jwt-token 生成器
 *
 * @author Agao
 * @date 2024/2/13 13:35
 */
@Slf4j
@Component
public class JwtCodec implements JwtDecoder, InitializingBean {
    public static final String PARTIAL_GRANTED_AUTHORITY_SUFFIX = ".SCOPE";
    private static final String SIGN_ALGO = "HmacSHA512";
    private static final Integer CLOCK_SKEW_SECONDS = 5;
    private JWSSigner signer;
    private NimbusJwtDecoder decoder;
    @Autowired
    private SettingCache settingCache;
    @Autowired
    private BlackSessionTokenValidator blackSessionTokenValidator;
    @Autowired
    private TimeoutTokenValidator timeoutValidator;

    /**
     * 编码认证token
     *
     * @param user     认证user
     * @param expire   token超时时间
     * @param timeUnit 单位
     * @return token
     */
    public String encodeForAccessToken(AuthUser user, Long expire, TimeUnit timeUnit) {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer("web")
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("nickname", user.getNickname())
                .claim("mobile", user.getMobile())
                .claim("roles", user.getRoles())
                .claim("sessionId", user.getSessionId())
                .claim("enabled", user.isEnabled())
                .claim("rememberMe", user.isRememberMe())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + timeUnit.toMillis(expire)))
                .build();

        try {
            return getSignedJWT(claimsSet);
        } catch (JOSEException e) {
            log.error("fail to sign jwt token of authUser ,username:{}, exception:{}", user.getUsername(), e.getMessage());
        }
        return null;
    }

    /**
     * 编码刷新token
     *
     * @param user     认证user
     * @param expire   token超时时间
     * @param timeUnit 单位
     * @return token
     */
    public String encodeForRefreshToken(AuthUser user, Long expire, TimeUnit timeUnit) {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer("web")
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("nickname", user.getNickname())
                .claim("mobile", user.getMobile())
                .claim("sessionId", user.getSessionId())
                .claim("enabled", user.isEnabled())
                .claim("rememberMe", user.isRememberMe())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + timeUnit.toMillis(expire)))
                .build();
        try {
            return getSignedJWT(claimsSet);
        } catch (JOSEException e) {
            log.error("fail to sign jwt token of authUser ,username:{}, exception:{}", user.getUsername(), e.getMessage());
        }
        return null;
    }

    private SecretKey getSecretKey() {
        String secretKey = settingCache.getConfigValue(
                SettingConst.CFG_KEY_AUTH_TOKEN_CODEC_SECRET,
                SettingConst.CFG_DEFAULT_AUTH_TOKEN_CODEC_SECRET);
        byte[] rawKey = BaseEncoding.base64().decode(secretKey);
        byte[] newKey = Arrays.copyOf(rawKey, 256);
        return new SecretKeySpec(newKey, SIGN_ALGO);
    }

    private String getSignedJWT(JWTClaimsSet claimsSet) throws JOSEException {
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build(), claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        return decoder.decode(token);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SecretKey key = getSecretKey();
        signer = new MACSigner(key);
        this.decoder = NimbusJwtDecoder.withSecretKey(key).build();
        this.decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                ImmutableList.of(
                        new JwtTimestampValidator(Duration.of(CLOCK_SKEW_SECONDS, ChronoUnit.SECONDS)),
                        timeoutValidator,
                        blackSessionTokenValidator)));
    }


}
