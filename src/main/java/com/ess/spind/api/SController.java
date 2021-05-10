package com.ess.spind.api;

import javax.naming.ldap.ManageReferralControl;

import com.ess.spind.dao.Manager;
import com.ess.spind.model.S;
import com.ess.spind.service.RequestService;
import com.ess.spind.service.SService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * SController
 */
@RequestMapping("/4327947293")
@RestController
public class SController {

    private final SService service;

    @Autowired
    private RequestService service2;

    @Autowired
    public SController(SService sService){
        this.service = sService;
    }

    @PostMapping
    public String checkUser(@RequestBody S model){
        if(Manager.register(model) && service2.checkClientId(model)) return service.checkData(model);
        else return "blocked";
    }
    
}