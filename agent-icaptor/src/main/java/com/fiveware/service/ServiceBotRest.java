package com.fiveware.service;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.model.OutTextRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by valdisnei on 29/05/17.
 */
@Service(value = "rest")
public class ServiceBotRest<T> implements ServiceBot<T>{

    static Logger logger = LoggerFactory.getLogger(ServiceBotRest.class);

    @Autowired
    private ServiceBotClassLoader serviceBotClassLoader;


    public OutTextRecord callBot(String nameBot, String endpoint, T parameter) throws ExceptionBot {
    	try {

    	    return serviceBotClassLoader.executeMainClass(nameBot,endpoint, parameter);
        } catch (IOException | ClassNotFoundException |
                IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            logger.error(" ServiceBotRest: ", e);
        }
        return null;
    }

    @Override
    public OutTextRecord callBot(String nameBot, T parameter) throws ExceptionBot {
        throw new IllegalArgumentException("Metodo nao permitdo para esta classe!");
    }
}