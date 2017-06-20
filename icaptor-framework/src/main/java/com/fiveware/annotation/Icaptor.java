package com.fiveware.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 
 * Indica como classe de automação  
 * 
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Component
public @interface Icaptor {

	String value();
	String classloader();
	String version();
	String description();
	
}
