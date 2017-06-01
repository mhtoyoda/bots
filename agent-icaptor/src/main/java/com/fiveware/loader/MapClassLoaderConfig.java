package com.fiveware.loader;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.model.BotClassLoaderContext;

@Component("mapClassLoaderConfig")
public class MapClassLoaderConfig implements ClassLoaderConfig {

	private static Map<String, BotClassLoaderContext> map = new ConcurrentHashMap<String, BotClassLoaderContext>();

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public Optional<BotClassLoaderContext> getPropertiesBot(String nameBot) throws ExceptionBot {
		BotClassLoaderContext botClassLoaderContext = map.get(nameBot);
		Optional<BotClassLoaderContext> optional = Optional.ofNullable(botClassLoaderContext);

		String message = messageSource.getMessage("bot.notFound",new Object[]{nameBot},null);
		return Optional.ofNullable(optional.orElseThrow(() -> new ExceptionBot(message)));
	}

	@Override
	public void savePropertiesBot(BotClassLoaderContext botClassLoaderContext){
		map.put(botClassLoaderContext.getClassLoader(), botClassLoaderContext);
	}
}
