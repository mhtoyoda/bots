package com.fiveware.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RetryJobTaskException implements JobTaskHandlerException  {

	static Logger log = LoggerFactory.getLogger(RetryJobTaskException.class);
	
	/**
	 * Error 500
	 */
	@Override
	public void handleException(Exception exception) {
		log.error(exception.getMessage());	
	}
}
