package com.spring.com.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientResource {
    @Value("${profile}")//绑定git仓库配置文件中的profile属性
    private String profile;
    @GetMapping("/profile")
    public String getProfile(){
        return profile;
    }

}
