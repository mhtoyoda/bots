package com.fiveware.metadata;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.fiveware.annotation.InputDictionary;

@Component
public class DataInputDictionary {
	
	public Object getValueAtribute(Object objeto, InputDictionaryAttribute attribute) {
		Class<? extends Object> clazz = objeto.getClass();
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(InputDictionary.class)) {
				InputDictionary inputDictionary = method.getAnnotation(InputDictionary.class);
				return attribute.getValue(inputDictionary);
			}
		}
		return null;
	}

	public enum InputDictionaryAttribute {
		
		TYPEFILEIN {
			@Override
			public Object getValue(InputDictionary inputDictionary) {
				return inputDictionary.typeFileIn();
			}
		},
		FIELDS {
			@Override
			public Object getValue(InputDictionary inputDictionary) {
				return inputDictionary.fields();
			}
		},
		SEPARATOR {
			@Override
			public Object getValue(InputDictionary inputDictionary) {
				return inputDictionary.separator();
			}
		};

		public abstract Object getValue(InputDictionary inputDictionary);
	}
}