package com.fiveware.metadata;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.fiveware.annotation.InputDictionary;

@Component
public class Dictionary {
	
	public String[] getFieldsInput(Object objeto) {
		Class<? extends Object> clazz = objeto.getClass();
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(InputDictionary.class)) {
				InputDictionary inputDictionary = method.getAnnotation(InputDictionary.class);
				String[] fields = inputDictionary.fields();
				return fields;
			}
		}
		return null;
	}
	
	public String getSeparatorInput(Object objeto) {
		Class<? extends Object> clazz = objeto.getClass();
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(InputDictionary.class)) {
				InputDictionary inputDictionary = method.getAnnotation(InputDictionary.class);
				String separator = inputDictionary.separator();
				return separator;
			}
		}
		return null;
	}
}