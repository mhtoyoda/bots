package com.fiveware.exceptionhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.annotation.Annotation;

@ControllerAdvice
public class AgentExceptionHandler implements ExceptionHandler{


	@Override
	public Class<? extends Throwable>[] value() {
		return new Class[0];
	}

	@Override
	public Class<? extends Annotation> annotationType() {
		return null;
	}
}
