package com.fiveware.bot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.model.Bot;
import com.fiveware.model.BotClassLoaderContext;
import com.google.common.collect.Lists;

@Component
public class BotContext {

	@Autowired
	@Qualifier("mapClassLoaderConfig")
	private ClassLoaderConfig classLoaderConfig;

	public List<Bot> bots() {
		List<Bot> bots = Lists.newArrayList();
		List<BotClassLoaderContext> list = classLoaderConfig.getAll();
		list.forEach(b -> {
			Bot bot = new Bot();
			bot.setNameBot(b.getNameBot());
			bot.setMethod(b.getMethod());			
			bots.add(bot);
		});
		return bots;
	}
}
