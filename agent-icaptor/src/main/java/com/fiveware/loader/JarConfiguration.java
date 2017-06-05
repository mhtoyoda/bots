package com.fiveware.loader;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.Automation;
import com.fiveware.exception.AttributeLoadException;
import com.fiveware.metadata.IcaptorMetaInfo;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.InputDictionaryContext;
import com.fiveware.model.OutputDictionaryContext;

@Component
public class JarConfiguration {

	static Logger logger= LoggerFactory.getLogger(JarConfiguration.class);

	@Autowired
	private ClassLoaderConfig classLoaderConfig;
	
	@SuppressWarnings("rawtypes")
	public void saveConfigurations(String pathJar) throws MalformedURLException, AttributeLoadException{		
		String nameBot = null, classLoaderInfo = null, nameJar = null;		
		ClassLoader classLoader = getClassLoader(pathJar);
		Set<Class<? extends Automation>> subTypesOf = getSubTypes(classLoader);
		for (Class<? extends Automation> clazz : subTypesOf) {
			try {
				Class<?> automationClass = classLoader.loadClass(clazz.getCanonicalName());
				Annotation[] annotations = automationClass.getAnnotations();
				for (Annotation annotation : annotations) {
					Class<? extends Annotation> annotationType = annotation.annotationType();					
					if (annotationType.getSimpleName().equals("Icaptor")) {	
						try {
							nameBot = (String) getValue(annotation, annotationType, IcaptorMetaInfo.VALUE.getValue());
							classLoaderInfo = (String) getValue(annotation, annotationType, IcaptorMetaInfo.CLASSLOADER.getValue());
							nameJar = StringUtils.substringAfterLast(pathJar, "/");														
						} catch (NoSuchMethodException | SecurityException | IllegalAccessException
								| InvocationTargetException e) {
							logger.error("Problema rotina saveconfiguration: ",e);
						}
					}
				}
				
				String method = (String) IcaptorMetaInfo.VALUE.getValueAtribute(automationClass, "IcaptorMethod");
				String endpoint = (String) IcaptorMetaInfo.ENDPOINT.getValueAtribute(automationClass, "IcaptorMethod");
				
				InputDictionaryContext inputDictionaryContext = getInputDictionaryAttributes(automationClass);				
				OutputDictionaryContext outputDictionaryContext = getOutputDictionaryAttributes(automationClass);
				
				saveAttributesClassLoader(nameBot, classLoaderInfo, nameJar, method, endpoint, inputDictionaryContext, outputDictionaryContext, getUrl(pathJar));
			} catch (ClassNotFoundException | AttributeLoadException e) {				
				throw new AttributeLoadException(e.getMessage());
			}			
		}
	}

	private void saveAttributesClassLoader(String nameBot, String classLoaderInfo, String nameJar, String method, String endpoint,
			InputDictionaryContext inputDictionaryContext, OutputDictionaryContext outputDictionaryContext,URL url) {
		BotClassLoaderContext botClassLoaderContext = new BotClassLoaderContext(nameBot, classLoaderInfo, method, endpoint, nameJar,
				inputDictionaryContext, outputDictionaryContext,url);
		classLoaderConfig.savePropertiesBot(botClassLoaderContext);
	}

	private OutputDictionaryContext getOutputDictionaryAttributes(Class<?> automationClass) throws AttributeLoadException {
		String typeFileOut = (String) IcaptorMetaInfo.TYPEFILEOUT.getValueAtribute(automationClass, "OutputDictionary");
		String[] fieldsOut = (String[]) IcaptorMetaInfo.FIELDS.getValueAtribute(automationClass, "OutputDictionary");
		String separatorOut = (String) IcaptorMetaInfo.SEPARATOR.getValueAtribute(automationClass, "OutputDictionary");
		String nameFileOut = (String) IcaptorMetaInfo.NAMEFILEOUT.getValueAtribute(automationClass, "OutputDictionary");
		OutputDictionaryContext outputDictionaryContext = new OutputDictionaryContext(typeFileOut, fieldsOut, separatorOut, nameFileOut);
		return outputDictionaryContext;
	}

	private InputDictionaryContext getInputDictionaryAttributes(Class<?> automationClass)
			throws AttributeLoadException {
		String typeFileIn = (String) IcaptorMetaInfo.TYPEFILEIN.getValueAtribute(automationClass, "InputDictionary");
		String[] fields = (String[]) IcaptorMetaInfo.FIELDS.getValueAtribute(automationClass, "InputDictionary");
		String separator = (String) IcaptorMetaInfo.SEPARATOR.getValueAtribute(automationClass, "InputDictionary");
		
		InputDictionaryContext inputDictionaryContext = new InputDictionaryContext(typeFileIn, fields, separator);
		return inputDictionaryContext;
	}

	protected Object getValue(Annotation annotation, Class<? extends Annotation> annotationType, String attribute)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Method methodTarget = annotationType.getMethod(attribute);
		Object value = methodTarget.invoke(annotation);
		return value;
	}

	protected ClassLoader getClassLoader(String pathJar) throws MalformedURLException {
		ClassLoader classLoader = new URLClassLoader(new URL[] { getUrl(pathJar) });
		return classLoader;
	}

	protected URL getUrl(String pathJar) throws MalformedURLException {
		File fileJar = new File(pathJar);
		return fileJar.toURI().toURL();
	}

	@SuppressWarnings("rawtypes")
	protected Set<Class<? extends Automation>> getSubTypes(ClassLoader classLoader) throws MalformedURLException{
		ConfigurationBuilder config = new ConfigurationBuilder().setUrls(ClasspathHelper.forClassLoader(classLoader))
				.addClassLoader(classLoader);
		Reflections reflections = new Reflections(config);
		Set<Class<? extends Automation>> subTypesOf = reflections.getSubTypesOf(Automation.class);
		return subTypesOf;
	}
	
	public void removeBot(String pathJar) {
		classLoaderConfig.removeBot(pathJar);					
	}
}