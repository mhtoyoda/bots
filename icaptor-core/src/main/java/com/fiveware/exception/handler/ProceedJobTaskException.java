package com.fiveware.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProceedJobTaskException implements JobTaskHandlerException {

	static Logger log = LoggerFactory.getLogger(ProceedJobTaskException.class);
	
	/**
	 * Continuar processamento
	 */
	@Override
	public void handleException(Throwable throwable) {
		log.error(throwable.getMessage());	
	}
}
