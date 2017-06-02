package com.fiveware.model;

import java.io.Serializable;
import java.net.URL;

public class BotClassLoaderContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5344664787240666880L;
	
	private String nameBot;
	private String classLoader;
	private String method;
	private String endpoint;
	private String nameJar;
	private InputDictionaryContext inputDictionary;
	private OutputDictionaryContext outputDictionary;
	private URL url;
	
	public BotClassLoaderContext(String nameBot, String classLoader, String method, String endpoint, String nameJar,
								 InputDictionaryContext inputDictionary, OutputDictionaryContext outputDictionary,
								 URL url) {
		this.nameBot = nameBot;
		this.classLoader = classLoader;
		this.method = method;
		this.endpoint = endpoint;
		this.nameJar = nameJar;
		this.inputDictionary = inputDictionary;
		this.outputDictionary = outputDictionary;
		this.url = url;
	}

	public String getNameBot() {
		return nameBot;
	}
	
	public String getClassLoader() {
		return classLoader;
	}

	public String getMethod() {
		return method;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public String getNameJar() {
		return nameJar;
	}

	public InputDictionaryContext getInputDictionary() {
		return inputDictionary;
	}

	public void setInputDictionary(InputDictionaryContext inputDictionary) {
		this.inputDictionary = inputDictionary;
	}

	public OutputDictionaryContext getOutputDictionary() {
		return outputDictionary;
	}

	public void setOutputDictionary(OutputDictionaryContext outputDictionary) {
		this.outputDictionary = outputDictionary;
	}

	public URL getUrl() {
		return url;
	}
}