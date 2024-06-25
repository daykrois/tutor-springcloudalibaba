package com.example.tutor02nacosdiscovery.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class ProviderController {
    @GetMapping("/provider/hello")
    public String hello() {
        return "nacos provider";
    }
}
