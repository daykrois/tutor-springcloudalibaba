package com.example.tutor01nacosconfig.demos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "lgd")
@Component
public class Lgd {
    private String mid;
    private String offlane;
    public String getMid() {
        return mid;
    }
    public void setMid(String mid) {
        this.mid = mid;
    }
    public String getOfflane() {
        return offlane;
    }
    public void setOfflane(String offlane) {
        this.offlane = offlane;
    }
    
    
}
