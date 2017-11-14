package com.fiveware.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Configura formatodores de atributos de classe do objeto referente a automação
 * 
 */
@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Formatter {
	
	String fieldIndex();
	String typeFile();
}
