package com.fiveware.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${worker.dir}")
    private String workdir;


    public OutTextRecord executeMainClass(String nameBot, T parameter) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExceptionBot {
        Optional<BotClassLoaderContext> botClassLoaderContext = classLoaderConfig.getPropertiesBot(nameBot);

        return executeMainClass(parameter, botClassLoaderContext);
    }
    
    public OutTextRecord executeMainClass(String nameBot,String endpoint, T parameter) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExceptionBot {
        Optional<BotClassLoaderContext> botClassLoaderContext = classLoaderConfig.getPropertiesBot(nameBot);

        if(!endpoint.equals(botClassLoaderContext.get().getEndpoint()))
            throw new ExceptionBot(messageSource.getMessage("endPoint.notFound",new Object[]{endpoint},null));

        return executeMainClass(parameter, botClassLoaderContext);
    }

    private OutTextRecord executeMainClass(T parameter, Optional<BotClassLoaderContext> botClassLoaderContext) throws ClassNotFoundException, ExceptionBot, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        ClassLoader classLoader = classLoaderRunner.loadClassLoader(botClassLoaderContext.get().getNameBot());
        Class clazz = classLoader.loadClass(botClassLoaderContext.get().getClassLoader());
        Class o = classLoader.loadClass(botClassLoaderContext.get().getTypeParameter().getName());

        Class<?> aClass = o.newInstance().getClass();
        Method execute = clazz.getMethod(botClassLoaderContext.get().getMethod(), aClass);

        Object o1 = objectMapper.convertValue(parameter, aClass);

        Object obj =  execute.invoke(clazz.newInstance(), o1);

        if (obj instanceof List) return new OutTextRecord(objectMapper.convertValue(obj, Map[].class)[0]);

        return new OutTextRecord(objectMapper.convertValue(obj, Map.class));
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