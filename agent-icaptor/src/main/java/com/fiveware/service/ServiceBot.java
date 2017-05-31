package com.fiveware.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

import com.fiveware.exception.ExceptionBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.loader.ClassLoaderRunner;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.OutTextRecord;

/**
 * Created by valdisnei on 29/05/17.
 */
@Service
public class ServiceBot<T> {

    static Logger logger = LoggerFactory.getLogger(ServiceBot.class);

    @Autowired
    private ObjectMapper objectMapper;  
    
    @Autowired
    private ClassLoaderConfig classLoaderConfig;
    
    @Autowired
    private ClassLoaderRunner classLoaderRunner;


    @Autowired
    private MessageSource messageSource;
    
    public OutTextRecord callBot(String nameBot, T parameter) throws ExceptionBot {
    	Optional<BotClassLoaderContext> botClassLoaderContext = classLoaderConfig.getPropertiesBot(nameBot);
    	try {        	
			return executeMainClass(botClassLoaderContext.get(), parameter);
        } catch (IOException | ClassNotFoundException |
                IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            logger.error(" ServiceBot: ", e);
        }
        return null;
    }
    
    public OutTextRecord callBot(String nameBot, String endpoint, T parameter) throws ExceptionBot {
    	Optional<BotClassLoaderContext> botClassLoaderContext = classLoaderConfig.getPropertiesBot(nameBot);
    	try {
        	if(!endpoint.equals(botClassLoaderContext.get().getEndpoint()))
        		throw new ExceptionBot(messageSource.getMessage("endPoint.notFound",new Object[]{endpoint},null));

				return executeMainClass(botClassLoaderContext.get(), parameter);

        } catch (IOException | ClassNotFoundException |
                IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            logger.error(" ServiceBot: ", e);
        }
        return null;
    }

    private OutTextRecord executeMainClass(BotClassLoaderContext botClassLoaderContext, T parameter) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExceptionBot {
        Class cls = classLoaderRunner.loadClassLoader(botClassLoaderContext.getNameBot());
        Method execute = cls.getMethod(botClassLoaderContext.getMethod(), parameter.getClass());
        Object obj =  execute.invoke(cls.newInstance(), parameter);
        Map map = (Map) objectMapper.convertValue(obj, Map.class);
        return new OutTextRecord(map);
    }


}