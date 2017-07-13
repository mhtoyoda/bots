package com.fiveware.service;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.model.OutTextRecord;
import com.fiveware.model.entities.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by valdisnei on 29/05/17.
 */
@Service(value = "rest")
public class ServiceBotRest implements ServiceBot{

    static Logger logger = LoggerFactory.getLogger(ServiceBotRest.class);

    @Autowired
    private ServiceBotClassLoader serviceBotClassLoader;

    @Autowired
    private RestTemplate restTemplate;


    public <T> OutTextRecord callBot(String nameBot, String endpoint, T parameter) throws ExceptionBot {
    	try {

    	    return serviceBotClassLoader.executeMainClass(nameBot,endpoint, parameter);
        } catch (ExceptionBot e){
    	    throw e;
    	} catch (IOException | ClassNotFoundException |
                IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            logger.error(" ServiceBotRest: ", e);
        }
        return null;
    }

    @Override
    public <T> OutTextRecord callBot(String nameBot, T parameter) throws ExceptionBot {
        throw new IllegalArgumentException("Metodo nao permitdo para esta classe!");
    }


    @Override
    public Optional<Bot> findByNameBot(String nameBot){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8085/api/bot/name/"+nameBot;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        Bot bot = restTemplate.getForObject(url, Bot.class);
        return Optional.of(bot);
    }


    @Override
    public Bot save(Bot bot){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8085/api/bot/save";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Bot> entity = new HttpEntity<Bot>(bot,headers);
        return restTemplate.postForObject(url, entity, Bot.class);
    }

}