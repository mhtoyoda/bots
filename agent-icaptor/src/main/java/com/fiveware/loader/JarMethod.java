package com.fiveware.loader;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.Automation;
import com.fiveware.exception.AttributeLoadException;
import com.fiveware.metadata.IcaptorMetaInfo;

@Component
public class JarMethod {

	@Autowired
	private JarConfiguration jarConfiguration;
	
	@SuppressWarnings("rawtypes")
	public void readConfigurations(String pathJar) throws MalformedURLException, AttributeLoadException {		
		ClassLoader classLoader = jarConfiguration.getClassLoader(pathJar);
		Set<Class<? extends Automation>> subTypesOf = jarConfiguration.getSubTypes(classLoader);
		for (Class<? extends Automation> clazz : subTypesOf) {
			try {
				Class<?> automationClass = classLoader.loadClass(clazz.getCanonicalName());
				Method[] methods = automationClass.getMethods();
				for (Method method : methods) {
					if(method.getName().equals("execute")){
						Parameter[] parameters = method.getParameters();
						for (Parameter parameter : parameters) {
							Annotation[] annotations = parameter.getAnnotations();
							for (Annotation annotation : annotations) {
								Class<? extends Annotation> annotationType = annotation.annotationType();
								if(annotationType.getSimpleName().equals("Field")){
									String name = (String) jarConfiguration.getValue(annotation, annotationType, IcaptorMetaInfo.NAME.getValue());
									System.out.println(name);
								}
							}
						}
					}
				}
			} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {				
				throw new AttributeLoadException(e.getMessage());
			}			
		}
	}
}
