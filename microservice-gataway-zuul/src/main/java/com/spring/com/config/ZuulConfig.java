package com.spring.com.config;

import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ZuulConfig {
    @Bean
    /*通过PatternServiceRouteMapper使用正则表达式指定Zuul的路由匹配规则，
    这里实现了诸如microservice-provider-user-v1这个微服务，映射到/v1/microservice-provider-user/**这个路径
    */
    public PatternServiceRouteMapper serviceRouteMapper(){
        return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)","${version}/${name}");
    }
}
