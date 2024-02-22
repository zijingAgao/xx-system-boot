package com.agao.security.jwt;

import com.agao.exception.user.UserExceptionCode;
import com.agao.security.cache.BlackSessionCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Component
public class BlackSessionTokenValidator implements OAuth2TokenValidator<Jwt> {

    @Autowired
    private BlackSessionCache blackSessionCache;

    @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {
        String sessionId = token.getClaim("sessionId");
        if (!StringUtils.hasLength(sessionId)) {
            log.warn("invalid token without sessionId, token:{}", token.getTokenValue());
            return OAuth2TokenValidatorResult.failure(new OAuth2Error(UserExceptionCode.INVALID_TOKEN.getCode().toString()));
        }
        Optional<String> userId = blackSessionCache.get(sessionId);
        if (userId.isPresent()) {
            return OAuth2TokenValidatorResult.failure(new OAuth2Error(UserExceptionCode.SESSION_OUT.getCode().toString()));
        }
        return OAuth2TokenValidatorResult.success();
    }
}
