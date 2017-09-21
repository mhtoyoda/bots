package com.fiveware.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.aop.framework.AopProxyUtils;

import com.fiveware.annotation.IcaptorParameter;
import com.fiveware.exception.AttributeLoadException;
import com.fiveware.helpers.ParameterContextBuilder;
import com.fiveware.model.IcaptorPameterContext;
import com.google.common.collect.Lists;

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
	TYPEPARAMETER("type"),
	NAMETYPEPARAMETER("nameTypeParameter"),
	EXCLUSIVE("exclusive"),
	CREDENTIAL("credential");

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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<IcaptorPameterContext> getValueAtributeParameters(Class clazz, String typeAnnotation) throws AttributeLoadException {
		List<IcaptorPameterContext> list = Lists.newArrayList();
		for (Method method : clazz.getDeclaredMethods()) {
			for (Annotation annotation : method.getAnnotations()) {
				Class<? extends Annotation> type = annotation.annotationType();
				if (type.getSimpleName().equals(typeAnnotation)) {					
					try {						
						Method methodTarget = type.getMethod(getValue());						
						Object[] values = (Object[]) methodTarget.invoke(annotation);	
						for(int index = 0; index < values.length; index++){
							Class<?>[] proxiedUserInterfaces = AopProxyUtils.proxiedUserInterfaces(values[index]);
							Class<IcaptorParameter> icaptorParameter = (Class<IcaptorParameter>) proxiedUserInterfaces[0];														
							String value  = (String) getValueParameter(values, index, icaptorParameter, VALUE.getValue());
							String regexValidate  = (String) getValueParameter(values, index, icaptorParameter, REGEXVALIDATE.getValue());
							String nameTypeParameter  = (String) getValueParameter(values, index, icaptorParameter, NAMETYPEPARAMETER.getValue());
							boolean exclusive = (Boolean) getValueParameter(values, index, icaptorParameter, EXCLUSIVE.getValue());
							boolean credential = (Boolean) getValueParameter(values, index, icaptorParameter, CREDENTIAL.getValue());
							
							ParameterContextBuilder builder = new ParameterContextBuilder();
							IcaptorPameterContext icaptorPameterContext = builder.value(value)
									.regexValidate(regexValidate).nameTypeParameter(nameTypeParameter)
									.exclusive(exclusive).credential(credential).build();
							list.add(icaptorPameterContext);
						}
						
						return list;
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException e) {
						throw new AttributeLoadException(e.getMessage());
					}
				}
			}
		}
		return null;
	}

	private Object getValueParameter(Object[] values, int i, Class<IcaptorParameter> icaptorParameter, String methodName)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Method method = icaptorParameter.getMethod(methodName);
		return method.invoke(values[i]);
	}
}
