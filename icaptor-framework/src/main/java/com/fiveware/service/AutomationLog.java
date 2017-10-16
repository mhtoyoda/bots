package com.fiveware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutomationLog {

    @Autowired
    private ServiceElasticSearch iCaptorLog;


    public String info(IcaptorLog log) {
        return iCaptorLog.log(log);
    }
}
