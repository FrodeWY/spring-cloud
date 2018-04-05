package com.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroServiceProviderUserWithAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceProviderUserWithAuthApplication.class, args);
	}
}
