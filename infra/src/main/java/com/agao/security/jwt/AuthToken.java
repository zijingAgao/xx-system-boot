package com.agao.security.jwt;

import com.agao.security.userdetails.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Agao
 * @date 2024/2/13 13:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpire;
    private AuthUser authUser;
}
