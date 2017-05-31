package com.fiveware.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Optional;

import com.fiveware.exception.ExceptionBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.service.LoadBot;

@Component
public class ClassLoaderRunner {

	static Logger logger = LoggerFactory.getLogger(ClassLoaderRunner.class);
	
	@Autowired
	private LoadBot botJar;
	
	@Autowired
	private ClassLoaderConfig classLoaderConfig;


	@Value("${worker.dir}")
	private String workDir;

	@Autowired
	private MessageSource messageSource;
	
	@SuppressWarnings({ "rawtypes", "resource" })
	public Class loadClassLoader(String botName) throws MalformedURLException, ClassNotFoundException, ExceptionBot {
		Optional<BotClassLoaderContext> context = classLoaderConfig.getPropertiesBot(botName);
		String className = context.get().getClassLoader();

		//TODO gambiarrinha so pra testar a rotina
		try {
			botJar.load(workDir + File.separator + context.get().getNameJar());
			ClassLoader classLoader = new URLClassLoader(new URL[] { botJar.getFile().toURI().toURL() });
			Class clazz = classLoader.loadClass(className);
			return clazz;
		} catch (FileNotFoundException e) {
			String message = messageSource.getMessage("botJar.notFound",new Object[]{workDir + File.separator + context.get().getNameJar()},null);
			logger.error(message);
			throw new ExceptionBot(message);
		}
	}
}
