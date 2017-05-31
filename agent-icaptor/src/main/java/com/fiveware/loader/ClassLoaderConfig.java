package com.fiveware.loader;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.model.BotClassLoaderContext;

import java.util.Optional;

public interface ClassLoaderConfig {

	Optional<BotClassLoaderContext> getPropertiesBot(String nameBot) throws ExceptionBot;

	void savePropertiesBot(BotClassLoaderContext botClassLoaderContext);
}
