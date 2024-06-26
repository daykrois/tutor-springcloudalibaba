package com.example.tutor03nacosdiscoveryconsumer.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.tutor03nacosdiscoveryconsumer.feign.ConsumerFeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class ConsumerController {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;
    
    @GetMapping("consumer/hello")
    public String test() {
        return restTemplate.getForObject("http://tutor02-nacos-discovery/provider/hello", String.class);
    }

    @Autowired
    private ConsumerFeign consumerFeign;

    @GetMapping("consumer/test")
    public String feignTest() {
        return consumerFeign.hello();
    }
    
    
}
