package com.ess.spind.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class S {
    private String name;
    private String pw;

    public S(
        @JsonProperty("name") String name, 
      @JsonProperty("pwd") String pw){
        this.name = name;
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public String getPw() {
        return pw;
    }
}
