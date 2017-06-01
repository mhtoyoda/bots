package com.fiveware.loader;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.fiveware.exception.ExceptionBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.InputDictionaryContext;
import com.fiveware.model.OutputDictionaryContext;

@Component("mapClassLoaderConfig")
public class MapClassLoaderConfig implements ClassLoaderConfig {

	private static Map<String, BotClassLoaderContext> map = new ConcurrentHashMap<String, BotClassLoaderContext>();

	@Autowired
	private MessageSource messageSource;


	static{
		InputDictionaryContext inputDictionary = new InputDictionaryContext("csv", new String[]{"cep"}, ",");
		OutputDictionaryContext outputDictionary = new OutputDictionaryContext("csv", new String[]{"logradouro", "bairro", "localidade", "cep"}, ",", "/home/fiveware/Documentos/saida.txt");
		
		map.put("consultaCEP", new BotClassLoaderContext("consultaCEP", "com.fiveware.TesteBot", "execute", 
				"correios-bot", "teste-bot-1.0-SNAPSHOT.jar", inputDictionary, outputDictionary));
	}
	
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
