package com.fiveware.metadata;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fiveware.Automation;
import com.fiveware.annotation.Icaptor;
import com.fiveware.annotation.IcaptorMethod;
import com.fiveware.annotation.IgnoreField;

public class DataObject {

	private Map<String, Object> mapAtributes;

	public Map<String, Object> loadParameters(Object object, Object...values) {
		mapAtributes = new LinkedHashMap<String, Object>();
		Class<?> clazz = object.getClass();
		if (clazz.isAnnotationPresent(Icaptor.class) || Automation.class.isAssignableFrom(clazz)) {
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.isAnnotationPresent(IcaptorMethod.class) || method.getName().equals("execute")) {
					Class<?>[] parameterTypes = method.getParameterTypes();
					for (Class<?> parameter : parameterTypes) {
						if (!parameter.isAnnotationPresent(IgnoreField.class) && Serializable.class.isAssignableFrom(parameter)) {
							System.out.println(method.getName());
							System.out.println(parameter.getName());
							System.out.println(values[0]);
						}
					}

				}
			}
		}

		return mapAtributes;
	}

	public Map<String, Object> getMapAtributes() {
		return mapAtributes;
	}
}
