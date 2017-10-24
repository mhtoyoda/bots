package com.fiveware.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.loader.ClassLoaderRunner;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.OutTextRecord;
import com.fiveware.parameter.ParameterIcaptor;
import com.fiveware.parameter.ParameterValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by valdisnei on 29/05/17.
 */
@Service
public class ResourceBot {

    static Logger logger = LoggerFactory.getLogger(ResourceBot.class);


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClassLoaderRunner classLoaderRunner;

    @Autowired
    private ClassLoaderConfig classLoaderConfig;

    @Autowired
    private MessageSource messageSource;

    public <T> OutTextRecord callBot(String nameBot, String endpoint, T parameter)
            throws RuntimeBotException, UnRecoverableException {
        try {
            return getOutTextRecord(nameBot, endpoint, parameter);
        } catch (RuntimeBotException e) {
            throw e;
        } catch (IOException | ClassNotFoundException |
                IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            logger.error(" ServiceBotRest: ", e);
        }
        return null;
    }


    public <T> OutTextRecord getOutTextRecord(String nameBot, String endpoint, T parameter)
            throws ClassNotFoundException, RuntimeBotException, IOException, InstantiationException, IllegalAccessException,
            NoSuchMethodException {

        Optional<BotClassLoaderContext> botClassLoaderContext = classLoaderConfig.getPropertiesBot(nameBot);

        if (!endpoint.equals(botClassLoaderContext.get().getEndpoint()))
            throw new RuntimeBotException(messageSource.getMessage("endPoint.notFound", new Object[]{endpoint}, null));


        ClassLoader classLoader = classLoaderRunner.loadClassLoader(botClassLoaderContext.get().getNameBot());
        Class clazz = classLoader.loadClass(botClassLoaderContext.get().getClassLoader());
        Class o = classLoader.loadClass(botClassLoaderContext.get().getTypeParameter().getName());
        Class param = classLoader.loadClass(ParameterValue.class.getName());

        Method execute = clazz.getMethod(botClassLoaderContext.get().getMethod(), o, param);

        Object obj = null;
        try {
            Object o1 = objectMapper.convertValue(parameter, o);

            Method listParam = param.getMethod("getParameterList");

            ParameterValue parameterValue = new ParameterValue();
            parameterValue.setParameterList((List<ParameterIcaptor>) listParam.invoke(param.newInstance()));

            obj = execute.invoke(clazz.newInstance(), o1, parameterValue);
        } catch (IllegalArgumentException e){
            throw new RuntimeBotException(messageSource.getMessage("endPoint.illegalArgument",
                    new Object[]{parameter,nameBot}, null));
        } catch (InvocationTargetException e) {
            throw new RuntimeBotException(e.getCause().getMessage());
        }

        if (obj instanceof List)
            return new OutTextRecord(objectMapper.convertValue(obj, Map[].class));

        Map map = objectMapper.convertValue(obj, Map.class);
        HashMap[] hashMaps = {(HashMap) map};

        return new OutTextRecord(hashMaps);

    }
}