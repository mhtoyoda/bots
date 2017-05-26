package com.fiveware.metadata;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.fiveware.annotation.OutputDictionary;

@Component
public class DataOutputDictionary {

	public Object getValueAtribute(Object objeto, OutputDictionaryAttribute attribute) {
		Class<? extends Object> clazz = objeto.getClass();
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(OutputDictionary.class)) {
				OutputDictionary outputDictionary = method.getAnnotation(OutputDictionary.class);
				return attribute.getValue(outputDictionary);
			}
		}
		return null;
	}

	public enum OutputDictionaryAttribute {
		TYPEFILEOUT {
			@Override
			public Object getValue(OutputDictionary outputDictionary) {
				return outputDictionary.typeFileOut();
			}
		},
		FIELDS {
			@Override
			public Object getValue(OutputDictionary outputDictionary) {
				return outputDictionary.fields();
			}
		},
		SEPARATOR {
			@Override
			public Object getValue(OutputDictionary outputDictionary) {
				return outputDictionary.separator();
			}
		},
		NAMEFILEOUT {
			@Override
			public Object getValue(OutputDictionary outputDictionary) {
				return outputDictionary.nameFileOut();
			}
		};

		public abstract Object getValue(OutputDictionary outputDictionary);
	}
}