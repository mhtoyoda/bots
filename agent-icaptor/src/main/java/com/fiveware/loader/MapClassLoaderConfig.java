package com.fiveware.loader;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fiveware.model.BotClassLoaderContext;

@Component("mapClassLoaderConfig")
public class MapClassLoaderConfig implements ClassLoaderConfig {

	private static Map<String, BotClassLoaderContext> map = new HashMap<String, BotClassLoaderContext>();
	
	static{
//		map.put("", new BotClassLoaderContext(classLoader, method, endpoint, workdir, nameJar));
	}
	
	@Override
	public BotClassLoaderContext getPropertiesBot(String nameBot) {		
		return map.get(nameBot);
	}

	@Override
	public void savePropertiesBot(BotClassLoaderContext icaptorClassLoader){
		map.put(icaptorClassLoader.getClassLoader(), icaptorClassLoader);
	}
}
