package com.spring.cloud.com.rest;

import com.netflix.appinfo.InstanceInfo;

import com.spring.cloud.com.config.UrlConfig;
import com.spring.cloud.com.domain.User;
import com.spring.cloud.com.feign_client.UserFeignClient;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;

@RestController
public class MovieController {
    private final UserFeignClient userFeignClient;
    private final Logger LOGGER=LoggerFactory.getLogger(this.getClass());

    public MovieController( UserFeignClient userFeignClient) {

        this.userFeignClient = userFeignClient;
    }

    /**
     * 构建Basic认证请求，获取服务端数据
     * microservice-provider-user用户微服务的虚拟主机名，默认注册到Eureka Server 上的应用名称为虚拟主机名
     * 可以使用配置属性eureka.instance.virtual-host-name或eureka.instance.secure-virtual-host-name指定虚拟主机名，
     * 当Ribbon和Eureka配合使用时，会自动将虚拟主机名映射成微服务的网络地址*/
    @GetMapping(value = "/user/{id}",produces = "application/json;charset=UTF-8")
    public User findById(@PathVariable Long id){
        User byId = userFeignClient.findById(id);
        return byId;
    }

    @GetMapping(value = "/getUser/{id}/{username}")
    public User getUser(@PathVariable Long id,@PathVariable String username){
        return userFeignClient.getUser(id,username);
    }

    @PostMapping(value = "/getUser")
    public User getUser(@RequestBody User user){
        return userFeignClient.getUser(user);
    }
}
