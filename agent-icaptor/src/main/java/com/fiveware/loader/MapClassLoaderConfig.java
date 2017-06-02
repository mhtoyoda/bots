package com.fiveware.loader;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.model.BotClassLoaderContext;

@Component("mapClassLoaderConfig")
public class MapClassLoaderConfig implements ClassLoaderConfig {

	private Logger log = LoggerFactory.getLogger(MapClassLoaderConfig.class);
	
	private static Map<String, BotClassLoaderContext> map = new ConcurrentHashMap<String, BotClassLoaderContext>();

	@Autowired
	private MessageSource messageSource;

	@Override
	public Optional<BotClassLoaderContext> getPropertiesBot(String nameBot) throws ExceptionBot {
		BotClassLoaderContext botClassLoaderContext = map.get(nameBot);
		Optional<BotClassLoaderContext> optional = Optional.ofNullable(botClassLoaderContext);

		String message = messageSource.getMessage("bot.notFound", new Object[]{nameBot}, null);
		return Optional.ofNullable(optional.orElseThrow(() -> new ExceptionBot(message)));
	}

	@Override
	public void savePropertiesBot(BotClassLoaderContext botClassLoaderContext){
		map.put(botClassLoaderContext.getNameBot(), botClassLoaderContext);
		log.info("Add [Bot]: {} - ClassLoader: {}", botClassLoaderContext.getNameBot(), botClassLoaderContext.getClassLoader());
	}

	@Override
	public void removeBot(String nameJar) {
		map.entrySet().forEach(c -> {
			if(c.getValue().getNameJar().equals(nameJar)){
				map.remove(c.getKey());
			}
		});				
	}
}
