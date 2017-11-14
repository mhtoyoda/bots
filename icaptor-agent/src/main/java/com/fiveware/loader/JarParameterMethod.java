package com.fiveware.loader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fiveware.Automation;
import com.fiveware.exception.AttributeLoadException;
import com.fiveware.metadata.IcaptorMetaInfo;

@Component
public class JarParameterMethod {

	static Logger logger = LoggerFactory.getLogger(JarParameterMethod.class);

	@SuppressWarnings("rawtypes")
	public void readConfigurations(ClassLoader classLoader, Class<? extends Automation> clazz)
			throws MalformedURLException, AttributeLoadException, IllegalAccessException {
		try {
			Class<?> automationClass = classLoader.loadClass(clazz.getCanonicalName());
			Method[] methods = automationClass.getMethods();
			for (Method method : methods) {
				if ("execute".equals(method.getName())) {
					Parameter parameter = method.getParameters()[0];
					logger.debug("Method Execute: {}", parameter.getType().isArray());					
					Annotation[] annotations = parameter.getAnnotations();
					for (Annotation annotation : annotations) {
						Class<? extends Annotation> annotationType = annotation.annotationType();

						if ("Field".equals(annotationType.getSimpleName())) {
							logger.debug("Field Type: {}", parameter.getType().getName());

							Class<?> fieldInstance = classLoader.loadClass(parameter.getType().getName());
							Field[] fields = fieldInstance.getDeclaredFields();
							for (Field field : fields) {
								logger.debug("Field Name: {}", field.getName());

								Annotation[] annotationsField = field.getAnnotations();
								for (Annotation a : annotationsField) {
									Class<? extends Annotation> ann = a.annotationType();

									if ("Field".equals(ann.getSimpleName())) {
										String name = (String) getValue(a, ann, IcaptorMetaInfo.NAME.getValue());
										logger.debug("Annotation: {}", name);

									}
								}
							}
						}
					}
				}
			}
		} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
				| InvocationTargetException e) {
			throw new AttributeLoadException(e.getMessage());
		}
	}
	
	private Object getValue(Annotation annotation, Class<? extends Annotation> annotationType, String attribute)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Method methodTarget = annotationType.getMethod(attribute);
		Object value = methodTarget.invoke(annotation);
		return value;
	}
}