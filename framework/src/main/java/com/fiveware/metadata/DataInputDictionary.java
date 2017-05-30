package com.fiveware.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.fiveware.exception.AttributeLoadException;

@Component
public class DataInputDictionary {
	
	@SuppressWarnings("rawtypes")
	public Object getValueAtribute(Class clazz, String nameMethod, InputDictionaryAttribute attribute) throws AttributeLoadException {
		for (Method method : clazz.getDeclaredMethods()) {
			if(method.getName().equals(nameMethod)){
				for (Annotation annotation : method.getAnnotations()) {
					Class<? extends Annotation> type = annotation.annotationType();
		            if(type.getSimpleName().equals("InputDictionary")){
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
		}	
		return null;
	}

	public enum InputDictionaryAttribute {
		
		TYPEFILEIN {
			@Override
			public String getName() {
				return "typeFileIn";
			}
		},
		FIELDS {
			@Override
			public String getName() {
				return "fields";
			}
		},
		SEPARATOR {
			@Override
			public String getName() {
				return "separator";
			}
			
		};

		public abstract String getName();
	}
}