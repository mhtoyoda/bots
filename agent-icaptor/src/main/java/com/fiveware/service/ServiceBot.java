package com.fiveware.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.loader.ClassLoaderRunner;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.OutTextRecord;
import com.google.common.base.Strings;

/**
 * Created by valdisnei on 29/05/17.
 */
@Service
public class ServiceBot<T>  {

    static Logger logger = LoggerFactory.getLogger(ServiceBot.class);

    @Autowired
    private ObjectMapper objectMapper;  
    
    @Autowired
    private ClassLoaderConfig classLoaderConfig;
    
    @Autowired
    private ClassLoaderRunner classLoaderRunner;
    
    public OutTextRecord callBot(String nameBot, T parameter) {
    	BotClassLoaderContext botClassLoaderContext = classLoaderConfig.getPropertiesBot(nameBot);
    	try {        	
        	if(botClassLoaderContext != null){
        		return executeMainClass(botClassLoaderContext, parameter);        		
        	}
        } catch (IOException | ClassNotFoundException |
                IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            logger.error(" ServiceBot: ", e);
        }
        return null;
    }
    
    public OutTextRecord callBot(String nameBot, String endpoint, T parameter) {
    	BotClassLoaderContext botClassLoaderContext = classLoaderConfig.getPropertiesBot(nameBot);
    	try {        	
        	if(hasEndPoint(botClassLoaderContext, endpoint)){
        		return executeMainClass(botClassLoaderContext, parameter);        		
        	}
        } catch (IOException | ClassNotFoundException |
                IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            logger.error(" ServiceBot: ", e);
        }
        return null;
    }

    private OutTextRecord executeMainClass(BotClassLoaderContext botClassLoaderContext, T parameter) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class cls = classLoaderRunner.loadClassLoader(botClassLoaderContext.getNameBot());
        Method execute = cls.getMethod(botClassLoaderContext.getMethod(), parameter.getClass());
        Object obj =  execute.invoke(cls.newInstance(), parameter);
        Map map = (Map) objectMapper.convertValue(obj, Map.class);
        return new OutTextRecord(map);
    }

    private Boolean hasEndPoint(BotClassLoaderContext botClassLoaderContext, String endpoint) {            	
    	if (Strings.isNullOrEmpty(botClassLoaderContext.getEndpoint()) ||
    			!endpoint.equals(botClassLoaderContext.getEndpoint()))
    		return Boolean.FALSE;        	
        
        return Boolean.TRUE;	
    }

}