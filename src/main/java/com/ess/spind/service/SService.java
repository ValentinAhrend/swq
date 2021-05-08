package com.ess.spind.service;

import com.ess.spind.dao.SDao;
import com.ess.spind.model.S;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * SService
 */
@Service
public class SService {

    private final SDao sDao;

    @Autowired
    public SService(@Qualifier("d300") SDao sDao){
        this.sDao = sDao;
    }

    public String checkData(S data){
        return sDao.checkS(data);
    }
    
}