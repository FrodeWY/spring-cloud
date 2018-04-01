package com.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/*1.register-with-eureka:表示是否将自己注册到Eureka Server,默认为true
2.fetch-registry:表示是否从Eureka Server 获取注册信息，默认为true，由于当前应用是单点的Eureka Server，不需要同步其他的Eureka Server 节点的数据，故设置为false
3.service-url:设置与Eureka Server 交互的网址，查询服务和注册服务都需要依赖这个地址，默认http://localhost:port/eureka；多个地址用','隔开*/
@EnableEurekaServer
@SpringBootApplication
public class MicroservieceDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservieceDiscoveryApplication.class, args);
	}
}
