package com.agao.user.service;

import com.agao.exception.user.UserException;
import com.agao.exception.user.UserExceptionCode;
import com.agao.setting.SettingConst;
import com.agao.setting.cache.SettingCache;
import com.agao.user.enums.PasswordStrength;
import com.agao.utils.StringRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 处理密码相关业务
 *
 * @author Agao
 * @date 2024/2/15 21:39
 */
@Service
public class PasswordService {
    @Autowired
    private SettingCache settingCache;
    /**
     * 密码加密器
     */
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    /**
     * 校验密码强度
     * 强密码一定满足弱密码要求
     *
     * @param pwd 密码明文
     */
    public void validatePwdStrength(String pwd) {
        if (!StringUtils.hasText(pwd)) {
            throw new UserException(UserExceptionCode.USER_PASSWORD_EMPTY);
        }
        PasswordStrength pwdStrength = PasswordStrength.getPwdStrength(pwd);
        // 系统配置的密码强度
        PasswordStrength sysPwdStrength = obtainSysPwdStrength();
        if (pwdStrength.equals(PasswordStrength.UNKNOWN)) {
            throw new UserException(UserExceptionCode.USER_PASSWORD_STRENGTH_NOT_MATCH);
        }
        // 如果系统配置的密码强度为高，而用户密码强度为低，则抛出异常
        if (sysPwdStrength.equals(PasswordStrength.HIGH) && pwdStrength.equals(PasswordStrength.LOW)) {
            throw new UserException(UserExceptionCode.USER_PASSWORD_STRENGTH_NOT_MATCH);
        }
    }

    /**
     * 获取系统密码强度
     *
     * @return 密码强度
     */
    public PasswordStrength obtainSysPwdStrength() {
        boolean highStrength = settingCache.getConfigValueAsBoolean(
                SettingConst.CFG_KEY_USER_PASSWORD_STRENGTH,
                SettingConst.CFG_DEFAULT_USER_PASSWORD_STRENGTH);
        return highStrength ? PasswordStrength.HIGH : PasswordStrength.LOW;
    }

    public String generateEncodeRandomPwd() {
        return PASSWORD_ENCODER.encode(generateRandomPwd());
    }

    /**
     * 随机生成密码，一定满足强密码要求
     *
     * @return 密码明文
     */
    private String generateRandomPwd() {
        String pwd = StringRandom.randomAlphanumeric(12);
        while (!PasswordStrength.HIGH.matches(pwd)) {
            pwd = StringRandom.randomAlphanumeric(12);
        }
        return pwd;
    }
}
