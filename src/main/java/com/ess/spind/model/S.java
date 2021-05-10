package com.ess.spind.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class S {
    private String name;
    private String pw;
    private String id;
    private String ip;

    public S(
        @JsonProperty("name") String name, 
        @JsonProperty("pwd") String pw){
        this.name = name;
        this.pw = pw;
        this.id = UUID.randomUUID().toString();
        this.ip =  IpUtils.getClientIp(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
    }

    public String getName() {
        return name;
    }

    public String getPw() {
        return pw;
    }

    public String getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }
}
