package com.fiveware.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Configura parametros referente a automação
 * 
 */
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = IcaptorParameters.class)
public @interface IcaptorParameter {
	
	String value();
	String regexValidate() default "";
	String nameTypeParameter();
	boolean exclusive();
	boolean credential();
}
