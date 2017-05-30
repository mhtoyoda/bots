package com.fiveware.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.fiveware.exception.AttributeLoadException;

@Component
public class DataIcaptorMethod {
	
	@SuppressWarnings("rawtypes")
	public Object getValueAtribute(Class clazz, IcaptorMethodAttribute attribute) throws AttributeLoadException {
		for (Method method : clazz.getDeclaredMethods()) {
			for (Annotation annotation : method.getAnnotations()) {
				Class<? extends Annotation> type = annotation.annotationType();
	            if(type.getSimpleName().equals("IcaptorMethod")){
	            	try {
						Method methodTarget = type.getMethod(attribute.getName());							
						Object value = methodTarget.invoke(annotation);
						return value;
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {							
						throw new AttributeLoadException(e.getMessage());
					}		        
	            }
			}
		}	
		return null;
	}

	public enum IcaptorMethodAttribute {
		
		VALUE {
			@Override
			public String getName() {
				return "value";
			}
		};

		public abstract String getName();
	}
}