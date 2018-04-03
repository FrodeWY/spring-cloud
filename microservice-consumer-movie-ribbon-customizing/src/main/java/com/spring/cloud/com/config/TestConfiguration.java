package com.spring.cloud.com.config;

import com.spring.cloud.ribbon.RibbonConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.netflix.ribbon.RibbonClient;


/**
 * 使用@RibbonClient,为特定name的Ribbon Client 自定义配置
 * 使用@RibbonClient 的configuration属性，指定Ribbon的配置类*/
@Configurable
@RibbonClient(name = "microservice-provider-user",configuration = RibbonConfig.class)
public class TestConfiguration {
}
