package com.fiveware.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RetryAfterTimeJobTaskException implements JobTaskHandlerException {

	static Logger log = LoggerFactory.getLogger(RetryAfterTimeJobTaskException.class);
	
	/**
	 * Error Intermittent
	 */
	@Override
	public void handleException(Throwable throwable) {
		log.error(throwable.getMessage());	
	}
}
