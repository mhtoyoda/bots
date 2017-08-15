package com.fiveware.loader;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.exception.RuntimeBotException;
import com.fiveware.model.BotClassLoaderContext;

@Component
public class ClassLoaderRunner {

	static Logger logger = LoggerFactory.getLogger(ClassLoaderRunner.class);

	@Autowired
	private ClassLoaderConfig classLoaderConfig;

	@SuppressWarnings({ "rawtypes", "resource" })
	public Class loadClass(String botName) throws ClassNotFoundException, RuntimeBotException, IOException {
		Optional<BotClassLoaderContext> context = classLoaderConfig.getPropertiesBot(botName);
		String className = context.get().getClassLoader();
		ClassLoader classLoader = new URLClassLoader(new URL[] { context.get().getUrl() });
		Class clazz = classLoader.loadClass(className);
		return clazz;
	}

	public ClassLoader loadClassLoader(String botName) throws ClassNotFoundException, RuntimeBotException, IOException {
		Optional<BotClassLoaderContext> context = classLoaderConfig.getPropertiesBot(botName);
		ClassLoader classLoader = new URLClassLoader(new URL[] { context.get().getUrl() });
		return classLoader;
	}
}