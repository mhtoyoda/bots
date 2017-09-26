package com.fiveware.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiUrlPersistence {
    @Autowired
    private ICaptorApiProperty iCaptorApiProperty;


    public String endPoint(String baseUrl, String endpoint){
        // @formatter:on
        return String.format("%s/api/%s",iCaptorApiProperty.getDataSource().getHost()
                ,baseUrl.concat(endpoint));
        // @formatter:off
    }

    public String endPointElasticSearch(String baseUrl, String endpoint){
        // @formatter:on
        return String.format("%s/%s",
                "http://".concat(iCaptorApiProperty.getBroker().getHost()).concat(":9200")
                ,baseUrl.concat(endpoint));
        // @formatter:off
    }
}