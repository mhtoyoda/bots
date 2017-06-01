package com.fiveware.loader;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.service.LoadBot;

@Component
public class ClassLoaderRunner {

	static Logger logger = LoggerFactory.getLogger(ClassLoaderRunner.class);
	
	@Autowired
	private LoadBot loadBot;
	
	@Autowired
	private ClassLoaderConfig classLoaderConfig;

	@SuppressWarnings({ "rawtypes", "resource" })
	public Class loadClassLoader(String botName) throws ClassNotFoundException, ExceptionBot, IOException {
		Optional<BotClassLoaderContext> context = classLoaderConfig.getPropertiesBot(botName);
		String className = context.get().getClassLoader();
		ClassLoader classLoader = new URLClassLoader(new URL[] { loadBot.getFile().toURI().toURL() });
		Class clazz = classLoader.loadClass(className);
		return clazz;
	}
}