package com.fiveware.loader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fiveware.Automation;
import com.fiveware.exception.AttributeLoadException;
import com.fiveware.metadata.IcaptorMetaInfo;
import com.fiveware.model.BotReturnTypeFormatter;
import com.google.common.collect.Lists;

@Component
public class JarReturnTypeMethod {

	static Logger logger = LoggerFactory.getLogger(JarReturnTypeMethod.class);

	@SuppressWarnings("rawtypes")
	public List<BotReturnTypeFormatter> readReturnTypeConfigurations(ClassLoader classLoader, Class<? extends Automation> clazz)
			throws MalformedURLException, AttributeLoadException, IllegalAccessException {
		List<BotReturnTypeFormatter> list = Lists.newArrayList();
		try {
			Class<?> automationClass = classLoader.loadClass(clazz.getCanonicalName());
			Method[] methods = automationClass.getMethods();
			for (Method method : methods) {
				if ("execute".equals(method.getName())) {
					Class<?> returnType = method.getReturnType();
					Class<?> returnTypeClass = classLoader.loadClass(returnType.getName());
					Field[] fields = returnTypeClass.getDeclaredFields();
					for (Field field : fields) {
						logger.debug("Field Name: {}", field.getName());
						Annotation[] annotationsField = field.getAnnotations();
						for (Annotation a : annotationsField) {							
							Class<? extends Annotation> ann = a.annotationType();
							if ("Formatter".equals(ann.getSimpleName())) {
								String fieldIndex = (String) getValue(a, ann, IcaptorMetaInfo.FIELDINDEX.getValue());
								String typeFile = (String) getValue(a, ann, IcaptorMetaInfo.TYPEFILE.getValue());
								logger.debug("Annotation: {} - {}", fieldIndex, typeFile);
								BotReturnTypeFormatter returnTypeFormatter = new BotReturnTypeFormatter(fieldIndex, typeFile, field.getName());
								list.add(returnTypeFormatter);
							}
						}
					}
				}
			}
		} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
				| InvocationTargetException e) {
			throw new AttributeLoadException(e.getMessage());
		}
		return list;
	}

	private Object getValue(Annotation annotation, Class<? extends Annotation> annotationType, String attribute)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Method methodTarget = annotationType.getMethod(attribute);
		Object value = methodTarget.invoke(annotation);
		return value;
	}
}