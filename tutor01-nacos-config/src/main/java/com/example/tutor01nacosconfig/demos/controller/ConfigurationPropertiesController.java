package com.example.tutor01nacosconfig.demos.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.tutor01nacosconfig.demos.config.Lgd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RefreshScope
public class ConfigurationPropertiesController {

    @Autowired
    private Lgd lgd;
    
    @GetMapping("lgd/mid")
    public String test() {
        return lgd.getMid();
    }

}
