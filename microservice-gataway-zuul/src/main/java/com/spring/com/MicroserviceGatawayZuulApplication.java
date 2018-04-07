package com.spring.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class MicroserviceGatawayZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceGatawayZuulApplication.class, args);
    }
}
