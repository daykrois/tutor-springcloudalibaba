package com.example.tutor03nacosdiscoveryconsumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("tutor02-nacos-discovery")
public interface ConsumerFeign {
    @GetMapping("provider/hello")
    public String hello();
}
