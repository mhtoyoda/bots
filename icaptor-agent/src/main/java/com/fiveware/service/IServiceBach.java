package com.fiveware.service;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.model.OutTextRecord;
import com.fiveware.processor.ProcessorFields;
import org.springframework.stereotype.Service;

/**
 * Created by valdisnei on 5/31/17.
 */
@Service
public interface IServiceBach {

    <T> OutTextRecord callBot(ProcessorFields processorFields, T parameter) throws ExceptionBot,UnRecoverableException;

    <T> OutTextRecord callBot(String nameBot, String endpoint, T parameter) throws ExceptionBot,UnRecoverableException;
}
