package com.spring.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class MicroserviceGatawayZuulFallbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceGatawayZuulFallbackApplication.class, args);
    }
}
