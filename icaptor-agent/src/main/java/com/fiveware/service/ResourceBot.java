package com.fiveware.service;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.model.OutTextRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by valdisnei on 29/05/17.
 */
@Service
public class ResourceBot {

    static Logger logger = LoggerFactory.getLogger(ResourceBot.class);

    @Autowired
    private ServiceBotClassLoader serviceBotClassLoader;

    public <T> OutTextRecord callBot(String nameBot, String endpoint, T parameter)
            throws ExceptionBot,UnRecoverableException {
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


}