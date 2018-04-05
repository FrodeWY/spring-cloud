package com.spring.cloud.com.rest;

import com.netflix.appinfo.InstanceInfo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.spring.cloud.com.config.UrlConfig;
import com.spring.cloud.com.domain.User;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;

@RestController
public class MovieController {
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;
    private final LoadBalancerClient loadBalancerClient;
    private final UrlConfig urlConfig;
    private final Logger LOGGER=LoggerFactory.getLogger(this.getClass());

    public MovieController(RestTemplate restTemplate, DiscoveryClient discoveryClient, LoadBalancerClient loadBalancerClient, UrlConfig urlConfig) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
        this.loadBalancerClient = loadBalancerClient;
        this.urlConfig = urlConfig;
    }

    /**
     * 构建Basic认证请求，获取服务端数据
     * microservice-provider-user用户微服务的虚拟主机名，默认注册到Eureka Server 上的应用名称为虚拟主机名
     * 可以使用配置属性eureka.instance.virtual-host-name或eureka.instance.secure-virtual-host-name指定虚拟主机名，
     * 当Ribbon和Eureka配合使用时，会自动将虚拟主机名映射成微服务的网络地址*/
//    fallbackMethod指定回退方法，commandProperties自定义属性配置HystrixCommand
    @HystrixCommand(fallbackMethod = "findByIdFallback", commandProperties = {
//            超时时间
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
//            断路器判断健康度时，手机信息所需要的持续时间
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
    }, threadPoolProperties = {
//            核心线程数
            @HystrixProperty(name = "coreSize", value = "2"),
//            线程池最大队列大小
            @HystrixProperty(name = "maxQueueSize", value = "10")
    })
    @GetMapping(value = "/user/{id}",produces = "application/json;charset=UTF-8")
    public User findById(@PathVariable Long id){
        HttpHeaders headers=new HttpHeaders();
        MediaType mediaType=MediaType.APPLICATION_JSON_UTF8;
        headers.setContentType(mediaType);
        headers.add("Accept",mediaType.toString());
        String username = urlConfig.getUsername();
        String password = urlConfig.getPassword();
        String plainCredentials= username+":"+password;
        String base64Credentials=Base64.getEncoder().encodeToString(plainCredentials.getBytes());
        headers.add("Authorization","Basic "+base64Credentials);
        HttpEntity<String > entity=new HttpEntity<String>(headers);
        ResponseEntity<User> exchange = restTemplate.exchange("http://microservice-provider-user/user/{id}", HttpMethod.GET, entity, User.class, id);
        return exchange.getBody();
    }
    /**
     * 查询microservice-provider-user 服务的信息并返回
     * @return  microservice-provider-user 服务的信息*/
    @GetMapping(value = "/user-instance",produces = "application/json;charset=UTF-8")
    public List<ServiceInstance> showInfo(){
        List<ServiceInstance> instances = this.discoveryClient.getInstances("microservice-provider-user");
        return instances;
    }
    /**
     * 通过日志可以看到请求会均匀地分不到两个微服务节点上，说明已经实现了均衡负载
     * */
    @GetMapping(value = "/log-instance")
    public void logUserInstance(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("microservice-provider-user");
//       打印当前选择的是哪个节点
        LOGGER.info("{}:{}:{}",serviceInstance.getServiceId(),serviceInstance.getHost(),serviceInstance.getPort());
    }

    public User findByIdFallback(Long id){
        User user=new User();
        user.setId(-1L);
        user.setName("默认用户");
        user.setAge(12);
        return user;
    }
}
