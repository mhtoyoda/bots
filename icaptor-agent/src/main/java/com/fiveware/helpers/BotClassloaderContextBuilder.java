package com.fiveware.helpers;

import java.net.URL;

import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.InputDictionaryContext;
import com.fiveware.model.OutputDictionaryContext;

public class BotClassloaderContextBuilder {

	private String nameBot;
	private String classLoader;
	private String method;
	private String endpoint;
	private String nameJar;	
	private InputDictionaryContext inputDictionaryContext;
	private OutputDictionaryContext outputDictionary;
	private URL url;
	private Class<?> typeParameter;
	
	public BotClassloaderContextBuilder(InputDictionaryContext inputDictionaryContext, OutputDictionaryContext outputDictionary){
		this.inputDictionaryContext = inputDictionaryContext;
		this.outputDictionary = outputDictionary;		
		this.endpoint = method;
	}
	
	public BotClassloaderContextBuilder nameBot(String nameBot){
		this.nameBot = nameBot;
		return this;
	}
	
	public BotClassloaderContextBuilder classLoader(String classLoader){
		this.classLoader = classLoader;
		return this;
	}
	
	public BotClassloaderContextBuilder method(String method){
		this.method = method;
		return this;
	}
	
	public BotClassloaderContextBuilder endpoint(String endpoint){
		this.endpoint = endpoint;
		return this;
	}
	
	public BotClassloaderContextBuilder nameJar(String nameJar){
		this.nameJar = nameJar;
		return this;
	}
	
	public BotClassloaderContextBuilder url(URL url){
		this.url = url;
		return this;
	}
	
	public BotClassloaderContextBuilder typeParameter(Class<?> typeParameter){
		this.typeParameter = typeParameter;
		return this;
	}
	
	public BotClassLoaderContext build(){
		return new BotClassLoaderContext(nameBot, classLoader, method, 
				endpoint, nameJar, inputDictionaryContext, outputDictionary, url, typeParameter); 
	}
}