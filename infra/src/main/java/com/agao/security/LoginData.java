package com.agao.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装登录返回对象
 *
 * @author Agao
 * @date 2024/2/13 12:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginData {
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 认证token
     */
    private String accessToken;
    /**
     * 刷新token
     */
    private String refreshToken;
    /**
     * 认证token 有效期
     */
    private Long accessTokenExpire;
    /**
     * 动作,eg: 双因子认证
     */
    private String action;
    /**
     * 等待时间 eg: 做个倒计时发送短信
     */
    private Long waitTime;
}
