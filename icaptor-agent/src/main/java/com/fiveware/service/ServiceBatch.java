package com.fiveware.service;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.model.OutTextRecord;
import com.fiveware.processor.ProcessorFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by valdisnei on 29/05/17.
 */
@Service("batch")
public class ServiceBatch implements IServiceBach {

    static Logger logger = LoggerFactory.getLogger(ServiceBatch.class);

    @Autowired
    private ServiceBotClassLoader serviceBotClassLoader;

    @Override
    public <T> OutTextRecord callBot(ProcessorFields processorFields, T parameter) throws ExceptionBot,UnRecoverableException {
        try {
            return serviceBotClassLoader.executeMainClass(processorFields, parameter);
        } catch (IOException | ClassNotFoundException |
                IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            logger.error(" ServiceBot: ", e);
        }
        return null;
    }

    @Override
    public <T> OutTextRecord callBot(String nameBot, String endpoint, T parameter) throws ExceptionBot {
        throw new IllegalArgumentException("Metodo nao permitdo para esta classe!");
    }


}