package com.agao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Agao
 * @date 2024/2/5 16:13
 */
@RestController
@RequestMapping("/api")
public class WebController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
