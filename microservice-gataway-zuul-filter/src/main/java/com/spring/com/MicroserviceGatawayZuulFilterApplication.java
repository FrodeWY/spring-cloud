package com.spring.com;

import com.spring.com.filter.PreRequestLogFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class MicroserviceGatawayZuulFilterApplication {
    @Bean
    public PreRequestLogFilter preRequestLogFilter(){
        return new PreRequestLogFilter();
    }
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceGatawayZuulFilterApplication.class, args);
    }
}
