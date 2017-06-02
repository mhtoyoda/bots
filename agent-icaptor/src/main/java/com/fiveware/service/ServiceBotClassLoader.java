package com.fiveware.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.loader.ClassLoaderRunner;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.OutTextRecord;

/**
 * Created by valdisnei on 29/05/17.
 */
@Service
public  class ServiceBotClassLoader<T> {

    static Logger logger = LoggerFactory.getLogger(ServiceBotClassLoader.class);

    @Autowired
    private ObjectMapper objectMapper;  
    
    @Autowired
    private ClassLoaderRunner classLoaderRunner;

    @Autowired
    private ClassLoaderConfig classLoaderConfig;

    @Autowired
    private MessageSource messageSource;


    public OutTextRecord executeMainClass(String nameBot, T parameter) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExceptionBot {
        Optional<BotClassLoaderContext> botClassLoaderContext = classLoaderConfig.getPropertiesBot(nameBot);

        Map map = executeMainClass(parameter, botClassLoaderContext);
        return new OutTextRecord(map);
    }
    
    public OutTextRecord executeMainClass(String nameBot,String endpoint, T parameter) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExceptionBot {
        Optional<BotClassLoaderContext> botClassLoaderContext = classLoaderConfig.getPropertiesBot(nameBot);

        if(!endpoint.equals(botClassLoaderContext.get().getEndpoint()))
            throw new ExceptionBot(messageSource.getMessage("endPoint.notFound",new Object[]{endpoint},null));

        Map map = executeMainClass(parameter, botClassLoaderContext);
        return new OutTextRecord(map);
    }

    private Map executeMainClass(T parameter, Optional<BotClassLoaderContext> botClassLoaderContext) throws ClassNotFoundException, ExceptionBot, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        Class cls = classLoaderRunner.loadClassLoader(botClassLoaderContext.get().getNameBot());
        Method execute = cls.getMethod(botClassLoaderContext.get().getMethod(), parameter.getClass());
        Object obj =  execute.invoke(cls.newInstance(), parameter);

        if (obj instanceof List)
            return objectMapper.convertValue(obj, Map[].class)[0];

        return objectMapper.convertValue(obj, Map.class);
    }
}