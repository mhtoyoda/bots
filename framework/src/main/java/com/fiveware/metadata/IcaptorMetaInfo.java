package com.fiveware.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.fiveware.exception.AttributeLoadException;

public enum IcaptorMetaInfo {
	
	VALUE("value"), 
	CLASSLOADER("classloader"),
	DESCRIPTION("description"),
	VERSION("version"),
	TYPEFILEIN("typeFileIn"),
	FIELDS("fields"),
	SEPARATOR("separator"),
	TYPEFILEOUT("typeFileOut"),
	NAMEFILEOUT("nameFileOut"),
	ENDPOINT("endpoint"),
	NAME("name"),
	LENGTH("length"),
	REGEXVALIDATE("regexValidate"),
	POSITION("position"),
	TYPEPARAMETER("type");

	private String value;

	private IcaptorMetaInfo(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	@SuppressWarnings("rawtypes")
	public Object getValueAtribute(Class clazz, String typeAnnotation) throws AttributeLoadException {

		for (Method method : clazz.getDeclaredMethods()) {
			for (Annotation annotation : method.getAnnotations()) {
				Class<? extends Annotation> type = annotation.annotationType();
				if (type.getSimpleName().equals(typeAnnotation)) {
					try {
						Method methodTarget = type.getMethod(getValue());
						Object value = methodTarget.invoke(annotation);
						return value;
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException e) {
						throw new AttributeLoadException(e.getMessage());
					}
				}
			}
		}
		return null;
	}
}
