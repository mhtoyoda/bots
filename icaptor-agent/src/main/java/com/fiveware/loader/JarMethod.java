package com.fiveware.loader;

import com.fiveware.Automation;
import com.fiveware.exception.AttributeLoadException;
import com.fiveware.metadata.IcaptorMetaInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.util.Set;

@Component
public class JarMethod {

	static Logger logger = LoggerFactory.getLogger(JarMethod.class);

	@Autowired
	private JarConfiguration jarConfiguration;
	
	@SuppressWarnings("rawtypes")
	public void readConfigurations(String pathJar) throws MalformedURLException, AttributeLoadException, IllegalAccessException {
		ClassLoader classLoader = jarConfiguration.getClassLoader(pathJar);
		Set<Class<? extends Automation>> subTypesOf = jarConfiguration.getSubTypes(classLoader);
		for (Class<? extends Automation> clazz : subTypesOf) {
			try {
				Class<?> automationClass = classLoader.loadClass(clazz.getCanonicalName());
				Method[] methods = automationClass.getMethods();
				for (Method method : methods) {
					if("execute".equals(method.getName())){
						Parameter parameter = method.getParameters()[0];	

						logger.debug("Method Execute: {}",parameter.getType().isArray());

						Annotation[] annotations = parameter.getAnnotations();
						for (Annotation annotation : annotations) {
							Class<? extends Annotation> annotationType = annotation.annotationType();

							if("Field".equals(annotationType.getSimpleName())){

								logger.debug("Field Type: {}",parameter.getType().getName());

								Class<?> endereco = classLoader.loadClass(parameter.getType().getName());
								Field[] fields = endereco.getDeclaredFields();
								for (Field field : fields) {

									logger.debug("Field Name: {}",field.getName());

									Annotation[] annotationsField = field.getAnnotations();
									for (Annotation a : annotationsField) {
										Class<? extends Annotation> ann = a.annotationType();

										if("Field".equals(ann.getSimpleName())){
											String name = (String) jarConfiguration.getValue(a, ann, IcaptorMetaInfo.NAME.getValue());
											logger.debug("Annotation: {}",name);

										}
									}
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
