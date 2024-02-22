package com.agao.security.jwt;

import com.agao.exception.user.UserExceptionCode;
import com.agao.security.cache.LoginExpiredCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Component
public class TimeoutTokenValidator implements OAuth2TokenValidator<Jwt> {

  @Autowired
  private LoginExpiredCache loginExpiredCache;

  @Override
  public OAuth2TokenValidatorResult validate(Jwt token) {
    Object o = token.getClaim("iat");
    if (o == null) {
      log.warn("invalid token without iat, tokenClaims:{}", token.getClaims());
      return OAuth2TokenValidatorResult.failure(new OAuth2Error(UserExceptionCode.INVALID_TOKEN.getCode().toString()));
    }
    Long timestamp = null;
    if (o instanceof Instant) {
      timestamp = ((Instant) o).getEpochSecond() * 1000;
    }
    if (timestamp == null) {
      log.warn("invalid token with error type in iat, tokenClaims:{}", token.getClaims());
      return OAuth2TokenValidatorResult.failure(new OAuth2Error(UserExceptionCode.INVALID_TOKEN.getCode().toString()));
    }
    String userId = token.getClaim("id");
    if (userId == null) {
      log.warn("invalid token without userId, tokenClaims:{}", token.getClaims());
      return OAuth2TokenValidatorResult.failure(new OAuth2Error(UserExceptionCode.INVALID_TOKEN.getCode().toString()));
    }
    Optional<Long> expireTime = loginExpiredCache.get(userId);

    if (expireTime.isPresent()) {
      return OAuth2TokenValidatorResult.success();
    }

    if (expireTime.get() > timestamp) {
      return OAuth2TokenValidatorResult.failure(new OAuth2Error(UserExceptionCode.INVALID_TOKEN.getCode().toString()));
    }
    return OAuth2TokenValidatorResult.success();
  }
}