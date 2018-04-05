package com.spring.cloud.com.rest;


import com.spring.cloud.com.domain.User;
import com.spring.cloud.com.feign_client.UserFeignClient;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Import(FeignClientsConfiguration.class)
public class MovieController {
    private final UserFeignClient userFeignClient;
    private final UserFeignClient adminFeignClient;
    private final Logger LOGGER=LoggerFactory.getLogger(this.getClass());

    public MovieController(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        this.userFeignClient = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract).
                requestInterceptor(new BasicAuthRequestInterceptor("user","123456")).target(UserFeignClient.class,"http://microservice-provider-user-with-auth/");
        this.adminFeignClient = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract).
                requestInterceptor(new BasicAuthRequestInterceptor("admin","123456")).target(UserFeignClient.class,"http://microservice-provider-user-with-auth/");
    }

    /**
     * 构建Basic认证请求，获取服务端数据
     * microservice-provider-user用户微服务的虚拟主机名，默认注册到Eureka Server 上的应用名称为虚拟主机名
     * 可以使用配置属性eureka.instance.virtual-host-name或eureka.instance.secure-virtual-host-name指定虚拟主机名，
     * 当Ribbon和Eureka配合使用时，会自动将虚拟主机名映射成微服务的网络地址*/
    @GetMapping(value = "/user-user/{id}",produces = "application/json;charset=UTF-8")
    public User findByIdUser(@PathVariable Long id){

        User byId = userFeignClient.findById(id);
        return byId;
    }

    @GetMapping(value = "/user-admin/{id}",produces = "application/json;charset=UTF-8")
    public User findByIdAdmin(@PathVariable Long id){

        User byId = adminFeignClient.findById(id);
        return byId;
    }

}
