package com.fiveware.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ValidationFieldException;

@Component("objectValidate")
public class ObjectValidate implements Validate {

	@Autowired
	private ValidateMethodField validateMethodField;

	@SuppressWarnings({ "rawtypes"})
	@Override
	public <T> void validate(T value, Class clazz) throws ValidationFieldException, AttributeLoadException {
		if (null != clazz) {
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				if ("execute".equals(method.getName())) {				
					Class<?> obj = value.getClass();
					Field[] fields = obj.getDeclaredFields();
					for (Field field : fields) {
						field.setAccessible(true);
						Annotation[] annotations = field.getDeclaredAnnotations();
						for (Annotation annotation : annotations) {
							Class<? extends Annotation> type = annotation.annotationType();
							if (type.getSimpleName().equals("Field")) {
								try {
									validateMethodField.validateRegex(annotation, type, field.get(value));
									validateMethodField.validateLength(annotation, type, field.get(value));
								} catch (IllegalArgumentException | IllegalAccessException e) {
									throw new AttributeLoadException(e.getMessage());
								}
							}
						}
					}
				}
			}
		}
	}
}