package com.agao.config;

import com.agao.enums.AclEntryPerm;
import com.agao.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * security废弃了 继承 WebSecurityConfigurerAdapter 的方式。改为直接链式编程注入bean
 * 链接：https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 *
 * @author Agao
 * @date 2024/2/5 17:13
 */
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private JsonLoginSuccessHandler successHandler;
    @Autowired
    private JsonLoginFailHandler failHandler;
    @Autowired
    private JsonLogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private JsonSessionInformationExpiredStrategy jsonSessionInformationExpiredStrategy;
    @Autowired
    private JsonAccessDeniedHandler jsonAccessDeniedHandler;
    @Autowired
    private JsonAuthenticationEntryPoint jsonAuthenticationEntryPoint;
    @Value("${xx-system.web.security.max-sessions:1}")
    private Integer maxSessions;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable()
                .authorizeHttpRequests()
                // 不需要认证的部分
                .antMatchers("/error").permitAll()
                .antMatchers("/login", "/api/login", "/api/login/captcha", "/api/manifest").permitAll()
                // 其他请求需要认证
                .anyRequest().hasAuthority(AclEntryPerm.AUTHED.name())
                .and()
                // 表单登录
                .formLogin()
                // 登录路由
                .loginProcessingUrl("/api/login")
                .successHandler(successHandler)
                .failureHandler(failHandler)
                .and()
                // 登出
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                // 异常
                .exceptionHandling()
                .accessDeniedHandler(jsonAccessDeniedHandler)
                .authenticationEntryPoint(jsonAuthenticationEntryPoint)
                .and()
                // 会话管理
                .sessionManagement()
                .maximumSessions(maxSessions)
                .expiredSessionStrategy(jsonSessionInformationExpiredStrategy)
        ;
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/swagger-ui/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/v3/**")
                .antMatchers("/doc.html")
                ;
    }

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
