package com.fiveware.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.loader.ClassLoaderRunner;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.OutTextRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    public OutTextRecord executeMainClass(String nameBot, T parameter) throws IOException,
            ClassNotFoundException, InstantiationException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException,
            ExceptionBot,UnRecoverableException {
        Optional<BotClassLoaderContext> botClassLoaderContext = classLoaderConfig.getPropertiesBot(nameBot);

        return executeMainClass(parameter, botClassLoaderContext);
    }

    public OutTextRecord executeMainClass(String nameBot,String endpoint, T parameter) throws IOException,
            ClassNotFoundException, InstantiationException, IllegalAccessException,
            NoSuchMethodException, ExceptionBot,UnRecoverableException {
        Optional<BotClassLoaderContext> botClassLoaderContext = classLoaderConfig.getPropertiesBot(nameBot);

        if(!endpoint.equals(botClassLoaderContext.get().getEndpoint()))
            throw new ExceptionBot(messageSource.getMessage("endPoint.notFound",new Object[]{endpoint},null));

        return executeMainClass(parameter, botClassLoaderContext);
    }

    private OutTextRecord executeMainClass(T parameter, Optional<BotClassLoaderContext> botClassLoaderContext)
            throws ClassNotFoundException, ExceptionBot,UnRecoverableException, NoSuchMethodException, IllegalAccessException,
            InstantiationException, IOException {

        ClassLoader classLoader = classLoaderRunner.loadClassLoader(botClassLoaderContext.get().getNameBot());
        Class clazz = classLoader.loadClass(botClassLoaderContext.get().getClassLoader());
        Class o = classLoader.loadClass(botClassLoaderContext.get().getTypeParameter().getName());

        Class<?> aClass = o.newInstance().getClass();
        Method execute = clazz.getMethod(botClassLoaderContext.get().getMethod(), aClass);
        
        Object o1 = null;
        if( null != parameter ){
            o1 = objectMapper.convertValue(parameter, aClass);
        }

        Object obj = null;
        try{
        	if( null != o1){
        		obj =  execute.invoke(clazz.newInstance(), o1);
        	}else{
        		obj =  execute.invoke(clazz.newInstance());
        	}
        }catch (InvocationTargetException e) {
            if (e.getCause().getClass().getName().equals(UnRecoverableException.class.getName())){
                UnRecoverableException unRecoverableException = new UnRecoverableException(e.getCause());
                Map map = new HashMap();
                map.put("ERROR","0:"+unRecoverableException.getMessage());
                HashMap[] hashMaps = {(HashMap) map};

                return new OutTextRecord(hashMaps);
            }else{
                throw new ExceptionBot(e.getTargetException().getMessage());
            }
        }

        if (obj instanceof List)
            return new OutTextRecord(objectMapper.convertValue(obj, Map[].class));

        Map map = objectMapper.convertValue(obj, Map.class);
        HashMap[] hashMaps = {(HashMap) map};

        return new OutTextRecord(hashMaps);
    }

    protected ClassLoader getClassLoader(String pathJar) throws MalformedURLException {
        ClassLoader classLoader = new URLClassLoader(new URL[] { getUrl(pathJar) });
        return classLoader;
    }

    protected URL getUrl(String pathJar) throws MalformedURLException {
        File fileJar = new File(pathJar);
        return fileJar.toURI().toURL();
    }
}