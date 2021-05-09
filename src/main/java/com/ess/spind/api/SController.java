package com.ess.spind.api;

import com.ess.spind.model.S;
import com.ess.spind.service.SService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SController
 */
@RequestMapping("/4327947293")
@RestController
public class SController {

    private final SService service;

    @Autowired
    public SController(SService sService){
        this.service = sService;
    }

    @GetMapping
    public String checkUser(@RequestBody S model){
        System.out.println("enter with user :"+model.getName()+"/"+model.getPw());
        return service.checkData(model);
    }
    
}