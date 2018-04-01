package com.spring.cloud.com.rest;

import com.netflix.appinfo.InstanceInfo;

import com.spring.cloud.com.domain.User;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class MovieController {
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    public MovieController(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @GetMapping(value = "/user/{id}",produces = "application/json;charset=UTF-8")
    public User findById(@PathVariable Long id){
        User user = restTemplate.getForObject("http://localhost:8080/user/{id}", User.class, id);
        return user;
    }
    /**
     * 查询microservice-provider-user 服务的信息并返回
     * @return  microservice-provider-user 服务的信息*/
    @GetMapping(value = "/user-instance",produces = "application/json;charset=UTF-8")
    public List<ServiceInstance> showInfo(){
        List<ServiceInstance> instances = this.discoveryClient.getInstances("microservice-provider-user");
        return instances;
    }
}
