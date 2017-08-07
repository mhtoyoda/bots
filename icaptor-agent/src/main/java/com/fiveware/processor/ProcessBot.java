package com.fiveware.processor;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.exception.Recoverable;

public interface ProcessBot<T> {

	void execute(String botName, T obj)
            throws IOException, AttributeLoadException, ClassNotFoundException, ExceptionBot,Recoverable;
}
