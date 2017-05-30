package com.fiveware.loader;

import com.fiveware.model.BotClassLoaderContext;

public interface ClassLoaderConfig {

	BotClassLoaderContext getPropertiesBot(String classLoader);

	void savePropertiesBot(BotClassLoaderContext icaptorClassLoader);
}
