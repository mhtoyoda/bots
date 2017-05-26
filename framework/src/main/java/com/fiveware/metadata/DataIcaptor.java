package com.fiveware.metadata;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.fiveware.annotation.Icaptor;

@Component
public class DataIcaptor {
	
	public Object getValueAtribute(Object objeto, InputDictionaryAttribute attribute) {
		Class<? extends Object> clazz = objeto.getClass();
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(Icaptor.class)) {
				Icaptor icaptor = method.getAnnotation(Icaptor.class);
				return attribute.getValue(icaptor);
			}
		}
		return null;
	}

	public enum InputDictionaryAttribute {
		VALUE {
			@Override
			public Object getValue(Icaptor icaptor) {
				return icaptor.value();
			}
		},
		CLASSLOADER {
			@Override
			public Object getValue(Icaptor icaptor) {
				return icaptor.classloader();
			}
		},
		VERSION {
			@Override
			public Object getValue(Icaptor icaptor) {
				return icaptor.version();
			}
		},
		DESCRIPTION {
			@Override
			public Object getValue(Icaptor icaptor) {
				return icaptor.description();
			}
		};

		public abstract Object getValue(Icaptor icaptor);
	}

}