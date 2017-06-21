package com.fiveware.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ValidationFieldException;

@Component("fieldValidate")
public class FieldValidate implements Validate {

	@Autowired
	private ValidateMethodField validateMethodField;
	
	@SuppressWarnings("rawtypes")
	@Override
	public <T> void validate(T value, Class clazz) throws ValidationFieldException, AttributeLoadException {
		if( null != clazz ){			
			for (Method method : clazz.getDeclaredMethods()) {
				Annotation[][] parameterAnnotations = method.getParameterAnnotations();
				for (Annotation[] annotations : parameterAnnotations) {
					for (Annotation annotation : annotations) {
						Class<? extends Annotation> type = annotation.annotationType();
						if (type.getSimpleName().equals("Field")) {
							validateMethodField.validateRegex(annotation, type, value);
							validateMethodField.validateLength(annotation, type, value);
						}						
					}
				}
			}
		}
	}
}