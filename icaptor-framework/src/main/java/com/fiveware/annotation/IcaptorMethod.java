package com.fiveware.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Indica método de classe de automação  
 * 
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IcaptorMethod {
	
	String value() default "execute";
	String endpoint() default "execute";
	Class<?> type();
}
