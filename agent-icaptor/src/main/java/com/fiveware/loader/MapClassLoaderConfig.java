package com.fiveware.loader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.InputDictionaryContext;
import com.fiveware.model.OutputDictionaryContext;

@Component("mapClassLoaderConfig")
public class MapClassLoaderConfig implements ClassLoaderConfig {

	private static Map<String, BotClassLoaderContext> map = new ConcurrentHashMap<String, BotClassLoaderContext>();
	
	static{
		InputDictionaryContext inputDictionary = new InputDictionaryContext("csv", new String[]{"cep"}, ",");
		OutputDictionaryContext outputDictionary = new OutputDictionaryContext("csv", new String[]{"logradouro", "bairro", "localidade", "cep"}, ",", "/home/fiveware/Documentos/saida.txt");
		
		map.put("consultaCEP", new BotClassLoaderContext("consultaCEP", "com.fiveware.TesteBot", "execute", 
				"correios-bot", "teste-bot-1.0-SNAPSHOT.jar", inputDictionary, outputDictionary));
	}
	
	@Override
	public BotClassLoaderContext getPropertiesBot(String nameBot) {		
		return map.get(nameBot);
	}

	@Override
	public void savePropertiesBot(BotClassLoaderContext botClassLoaderContext){
		map.put(botClassLoaderContext.getClassLoader(), botClassLoaderContext);
	}
}
