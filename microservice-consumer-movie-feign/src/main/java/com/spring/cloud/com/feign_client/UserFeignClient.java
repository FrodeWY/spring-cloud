package com.spring.cloud.com.feign_client;

import com.spring.cloud.com.domain.User;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.spring.cloud.feignconfig.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*configuration:指定自定义配置类*/
@FeignClient(name = "microservice-provider-user",configuration =FeignConfiguration.class)
public interface UserFeignClient {
//    注意这里@PathVariable必须标明对应路径上的那个参数，否则报错,如果要调用的接口有Accept,content-type，需要加consumes，produces
    @GetMapping(value = "/user/{id}",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
//    @RequestLine("GET /user/{id}")
    User findById(@PathVariable("id") Long id);

    @GetMapping(value = "/user-get",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    User getUser(@RequestParam("id") Long id,@RequestParam("username") String username);

    @PostMapping(value = "/user-post",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    User getUser(User user);
}
