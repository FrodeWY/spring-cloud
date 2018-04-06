package com.spring.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTurbine
public class MicroserviceHystrixTurbineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceHystrixTurbineApplication.class, args);
    }
}
