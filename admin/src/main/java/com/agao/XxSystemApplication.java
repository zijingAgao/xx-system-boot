package com.agao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Agao
 * @date 2024/2/5 16:11
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class XxSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(XxSystemApplication.class, args);
    }
}
