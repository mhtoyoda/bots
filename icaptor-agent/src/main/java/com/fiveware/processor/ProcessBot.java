package com.fiveware.processor;

import java.io.IOException;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.model.MessageBot;

public interface ProcessBot {

	void execute(String botName, MessageBot obj)
			throws IOException, AttributeLoadException, ClassNotFoundException, ExceptionBot;
}
