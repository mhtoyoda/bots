package com.fiveware.loader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.service.LoadBot;

@Component
public class ClassLoaderRunner {
	
	@Autowired
	private LoadBot botJar;
	
	@Autowired
	private ClassLoaderConfig classLoaderConfig;
	
	@SuppressWarnings({ "rawtypes", "resource" })
	public Class loadClassLoader(String botName) throws MalformedURLException, ClassNotFoundException {
		Class clazz = null;
		BotClassLoaderContext botClassLoaderContext = classLoaderConfig.getPropertiesBot(botName);
		if( null != botClassLoaderContext){
			String className = botClassLoaderContext.getClassLoader();
			ClassLoader classLoader = new URLClassLoader(new URL[] { botJar.getFile().toURI().toURL() });
			clazz = classLoader.loadClass(className);			
		}
		return clazz;
	}
}
