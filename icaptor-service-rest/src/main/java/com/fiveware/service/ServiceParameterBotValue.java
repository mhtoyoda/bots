package com.fiveware.service;

import com.fiveware.model.ParameterValueBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by valdisnei on 13/07/17.
 */
@Service
public class ServiceParameterBotValue {

    @Autowired
    private RestTemplate restTemplate;

    public List<ParameterValueBot> findByParameterBotValues(String nameBot) {

        String url = "http://localhost:8085/api/parameters/nameBot/"+nameBot;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        return restTemplate.getForObject(url, List.class);
    }
}
