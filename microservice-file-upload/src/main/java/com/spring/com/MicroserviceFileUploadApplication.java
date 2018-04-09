package com.spring.com;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.io.File;

@SpringBootApplication
@EnableDiscoveryClient
@PropertySource("classpath:application.yml")
public class MicroserviceFileUploadApplication {
	@Value("${parameters.upload-path}")
	private String uploadPath;
	@PostConstruct
	public void initApplication(){
		File file=new File(uploadPath);
		if(!file.exists()){
			file.mkdirs();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceFileUploadApplication.class, args);
	}
}
