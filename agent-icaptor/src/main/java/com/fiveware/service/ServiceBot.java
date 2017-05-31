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

	public static final String BOT_CLASS_MAIN = "bot.class.main";
    public static final String METHOD_EXECUTE = "execute";
    public static final String BOT_ENDPOINT = "bot.endpoint";
    public static final String BOT_NAME = "consultaCEP";

    static Logger logger = LoggerFactory.getLogger(ServiceBot.class);

    @Autowired
    private ObjectMapper objectMapper;  
    
    @Autowired
    private ClassLoaderConfig classLoaderConfig;
    
    @Autowired
    private ClassLoaderRunner classLoaderRunner;
    
    public OutTextRecord callBot(String nameBot, T parameter) {
        try {
        	if(hasEndPoint(nameBot)){
        		return executeMainClass(nameBot, parameter);        		
        	}

        } catch (IOException | ClassNotFoundException |
                IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            logger.error(" ServiceBot: ", e);
        }
        return null;
    }

    private OutTextRecord executeMainClass(String botName, T parameter) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class cls = classLoaderRunner.loadClassLoader(botName);
        Method execute = cls.getMethod(METHOD_EXECUTE, parameter.getClass());
        Object obj =  execute.invoke(cls.newInstance(), parameter);
        Map map = (Map) objectMapper.convertValue(obj, Map.class);
        return new OutTextRecord(map);
    }

    private Boolean hasEndPoint(String nameBot) {
        BotClassLoaderContext botClassLoaderContext = classLoaderConfig.getPropertiesBot(nameBot);
        if( null != botClassLoaderContext ){
        	if (Strings.isNullOrEmpty(botClassLoaderContext.getEndpoint()) ||
        			!nameBot.equals(botClassLoaderContext.getEndpoint()))
        		return Boolean.FALSE;        	
        }
        return Boolean.TRUE;	
    }

}