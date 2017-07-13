package com.fiveware.service;

import com.fiveware.model.entities.ParameterValueBot;
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

    public List<ParameterValueBot> findByParameterBotValues(String nameBot) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8085/api/bot/parameters/nameBot/"+nameBot;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        return restTemplate.getForObject(url, List.class);
    }
}
