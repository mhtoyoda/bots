package com.fiveware.validation;

import java.lang.reflect.Method;

import com.fiveware.annotation.InputDictionary;
import com.fiveware.annotation.OutputDictionary;

public class ValidationDictionary {

	private Class<?> clazz;

	public ValidationDictionary(Class<?> clazz) {
		this.clazz = clazz;
	}

	public boolean validateDictionary() {
		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(InputDictionary.class)) {
				
			}
			if (method.isAnnotationPresent(OutputDictionary.class)) {

			}
		}
		return true;
	}

}
