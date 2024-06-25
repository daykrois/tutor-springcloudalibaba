package com.example.tutor02nacosdiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Tutor02NacosDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(Tutor02NacosDiscoveryApplication.class, args);
    }

}
