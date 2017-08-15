package com.fiveware.service;

import com.fiveware.exception.RuntimeBotException;
import com.fiveware.model.OutTextRecord;
import com.fiveware.processor.ProcessorFields;

/**
 * Created by valdisnei on 5/31/17.
 */
public interface IServiceBach {
    <T> OutTextRecord callBot(ProcessorFields processorFields, T parameter) throws RuntimeBotException;
}
