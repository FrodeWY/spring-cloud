package com.spring.com.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class NodeController {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("node_test")
    public String findById(){
        return restTemplate.getForObject("http://microservice-sidecar-node-service/",String.class);
    }
}
