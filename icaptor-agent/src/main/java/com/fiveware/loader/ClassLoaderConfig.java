package com.fiveware.loader;

import com.fiveware.exception.RuntimeBotException;
import com.fiveware.model.BotClassLoaderContext;

import java.util.List;
import java.util.Optional;

public interface ClassLoaderConfig {

	Optional<BotClassLoaderContext> getPropertiesBot(String nameBot) throws RuntimeBotException;

	void savePropertiesBot(BotClassLoaderContext botClassLoaderContext);
	
	void removeBot(String nameBot);
	
	List<BotClassLoaderContext> getAll();
}
