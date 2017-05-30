package com.fiveware.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.loader.BotJar;
import com.fiveware.model.OutTextRecord;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

/**
 * Created by valdisnei on 29/05/17.
 */
@Service
public class ServiceBot<T> {

	public static final String BOT_CLASS_MAIN = "bot.class.main";
    public static final String METHOD_EXECUTE = "execute";
    public static final String BOT_ENDPOINT = "bot.endpoint";

    static Logger logger = LoggerFactory.getLogger(ServiceBot.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BotJar botJar;

    public OutTextRecord callBot(T parameter) {
        try {
            return executeMainClass(parameter);

        } catch (IOException | ClassNotFoundException |
                IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            logger.error(" ServiceBot: {}  ", e);
        }

        return null;
    }


    public OutTextRecord callBot(String endPoint,T parameter) {
            if (hasEndPoint(endPoint))
                return callBot(parameter);
        return null;
    }

    public Class loadClassLoader() throws MalformedURLException, ClassNotFoundException {
    	String className = botJar.getConfigProp(BOT_CLASS_MAIN);
    	ClassLoader classLoader = new URLClassLoader(new URL[]{botJar.getFile().toURI().toURL()});
    	Class cls = classLoader.loadClass(className);
    	return cls;
    }

    private OutTextRecord executeMainClass(T cep) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {      
        Class cls = loadClassLoader();
        Method execute = cls.getMethod(METHOD_EXECUTE, String.class);
        Object obj =  execute.invoke(cls.newInstance(), cep);

        Map map = (Map) objectMapper.convertValue(obj, Map.class);

        return new OutTextRecord(map);
    }

    private Boolean hasEndPoint(String endPoint) {

        if (Strings.isNullOrEmpty(botJar.getConfigProp(BOT_ENDPOINT)) ||
                !endPoint.equals(botJar.getConfigProp(BOT_ENDPOINT)))
            return Boolean.FALSE;

        return Boolean.TRUE;
    }

}