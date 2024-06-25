package com.example.tutor01nacosconfig.demos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RefreshScope
public class ValueController {
    @Value("${lgd.carry}")
    private String lgdcarry;

    

    @GetMapping("lgd/carry")
    public String test() {
        return lgdcarry;
    }
    
}
