package com.agao.config;

import com.agao.security.filter.TokenExpireRemindFilter;
import com.agao.security.handler.*;
import com.agao.security.jwt.CustomGrantedAuthoritiesConverter;
import com.agao.security.jwt.JwtCodec;
import com.agao.security.jwt.LoginExpiredAuthenticationEntryPoint;
import com.agao.security.userdetails.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
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
    private JsonAccessDeniedHandler jsonAccessDeniedHandler;
    @Autowired
    private JsonAuthenticationEntryPoint jsonAuthenticationEntryPoint;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private JwtCodec jwtCodec;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                // 不需要认证的部分
                .antMatchers("/error").permitAll()
                .antMatchers("/login", "/api/login", "/api/login/captcha", "/api/manifest").permitAll()
                // 其他请求需要认证
//                .anyRequest().hasAuthority(AclEntryPerm.AUTHED.name())
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer(oauth2 -> {
                    oauth2.authenticationEntryPoint(new LoginExpiredAuthenticationEntryPoint())
                            .jwt()
                            .decoder(jwtCodec)
                            .jwtAuthenticationConverter(jwtAuthenticationConverter());
                })
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
                // 关闭session会话管理
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                 token 过期提醒过滤器
                .addFilterAfter(new TokenExpireRemindFilter(), BearerTokenAuthenticationFilter.class)
//                .addFilterAfter()
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
                .antMatchers("/webjars/**")
                .antMatchers("/favicon.ico")
                ;
    }


    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsServiceImpl);
        provider.setPasswordEncoder(passwordEncoder());
        // 这里要隐藏系统默认的提示信息，否则一直显示账户或密码错误
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    @Bean
    protected JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new CustomGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
