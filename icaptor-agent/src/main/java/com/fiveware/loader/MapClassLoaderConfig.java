package com.fiveware.loader;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fiveware.exception.RuntimeBotException;
import com.fiveware.model.BotClassLoaderContext;
import com.google.common.collect.Lists;

@Component("mapClassLoaderConfig")
public class MapClassLoaderConfig implements ClassLoaderConfig {

	private Logger log = LoggerFactory.getLogger(MapClassLoaderConfig.class);
	
	private static final Map<String, BotClassLoaderContext> map = new ConcurrentHashMap<String, BotClassLoaderContext>();

	@Autowired
	private MessageSource messageSource;

	@Override
	public Optional<BotClassLoaderContext> getPropertiesBot(String nameBot) throws RuntimeBotException {
		BotClassLoaderContext botClassLoaderContext = map.get(nameBot);
		Optional<BotClassLoaderContext> optional = Optional.ofNullable(botClassLoaderContext);

		String message = messageSource.getMessage("bot.notFound", new Object[]{nameBot}, null);
		return Optional.ofNullable(optional.orElseThrow(() -> new RuntimeBotException(message)));
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
				log.info("Removed [Bot]: {} - ClassLoader: {}", c.getValue().getNameJar(),c.getValue().getClassLoader());

			}
		});

	}

	@Override
	public List<BotClassLoaderContext> getAll() {
		List<BotClassLoaderContext> list = Lists.newArrayList();
		map.entrySet().forEach(bot -> {
			list.add(bot.getValue());
		});
		return list;
	}
}
