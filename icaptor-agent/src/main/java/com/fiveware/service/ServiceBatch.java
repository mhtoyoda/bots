package com.fiveware.service;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.model.OutTextRecord;
import com.fiveware.processor.ProcessorFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by valdisnei on 29/05/17.
 */
@Service
public class ServiceBatch implements IServiceBach {

    static Logger logger = LoggerFactory.getLogger(ServiceBatch.class);

    @Autowired
    private ServiceBotClassLoader serviceBotClassLoader;

    @Override
    public <T> OutTextRecord callBot(ProcessorFields processorFields, T parameter) throws ExceptionBot {
        try {
            return serviceBotClassLoader.getOutTextRecord(parameter, processorFields);
        } catch (IOException | ClassNotFoundException |
                IllegalAccessException | InstantiationException | NoSuchMethodException  e) {
            logger.error(" ServiceBatch: ", e);
        }
        return null;
    }

}