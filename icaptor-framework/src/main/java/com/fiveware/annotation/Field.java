package com.fiveware.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Configura atributos de classe do objeto referente a automação
 * 
 */
@Target(value = {ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
	
	String name();
	int position() default 0;
	int length() default 0;
	String regexValidate() default "";
	
}
