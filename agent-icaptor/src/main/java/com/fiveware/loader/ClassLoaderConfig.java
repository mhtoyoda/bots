package com.fiveware.loader;

import com.fiveware.model.BotClassLoaderContext;

public interface ClassLoaderConfig {

	BotClassLoaderContext getPropertiesBot(String nameBot);

	void savePropertiesBot(BotClassLoaderContext botClassLoaderContext);
}
