package com.spring.cloud.feignconfig;

import feign.Contract;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;

@Configurable
public class FeignConfiguration {
    /*将契约改为feign的原生默认契约，这样就可以使用feign自带的注解*/
//    @Bean
//    public Contract feignConfract(){
//        return new Contract.Default();
//    }
    /*如果调用的接口有basic认证，需要配置BasicAuthRequestInterceptor*/
    @Bean
    public BasicAuthRequestInterceptor authRequestInterceptor(){
        return new BasicAuthRequestInterceptor("user","123456");
    }
}
