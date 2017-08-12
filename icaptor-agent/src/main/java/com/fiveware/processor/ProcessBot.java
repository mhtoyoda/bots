package com.fiveware.processor;

import java.io.IOException;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.exception.UnRecoverableException;

public interface ProcessBot<T> {

	void execute(String botName, T obj)
            throws IOException, AttributeLoadException, ClassNotFoundException, ExceptionBot;
}
