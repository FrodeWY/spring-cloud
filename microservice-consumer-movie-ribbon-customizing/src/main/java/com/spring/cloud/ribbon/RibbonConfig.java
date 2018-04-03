package com.spring.cloud.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
/**
 * 该类为Ribbon的配置类
 * 注意：该类不应该在主应用程序上下文的@ComponentScan 中。
 * 否则该类中的配置信息会被所有的@RibbonClient共享
 */
@Configurable
public class RibbonConfig {
    @Bean
    public IRule ribbonRule(){
        return new RandomRule();
    }
}
