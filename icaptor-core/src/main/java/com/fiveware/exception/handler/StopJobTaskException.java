package com.fiveware.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StopJobTaskException implements JobTaskHandlerException {

	static Logger log = LoggerFactory.getLogger(StopJobTaskException.class);
	
	/**
	 * Bot Out
	 * User/Password Invalid
	 */
	@Override
	public void handleException(Exception exception) {
		log.error(exception.getMessage());
	}

}
