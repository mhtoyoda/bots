package com.fiveware.processor;

import java.io.IOException;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ParameterInvalidException;
import com.fiveware.exception.RuntimeBotException;

public interface ProcessBot<T> {

	void execute(String botName, T obj)
            throws IOException, AttributeLoadException, ClassNotFoundException, RuntimeBotException, ParameterInvalidException;
}
