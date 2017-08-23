package com.fiveware.model;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

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
	private Class<?> typeParameter;
	private List<IcaptorPameterContext> pameterContexts;
	
	public BotClassLoaderContext(String nameBot, String classLoader, String method, String endpoint, String nameJar,
								 InputDictionaryContext inputDictionary, OutputDictionaryContext outputDictionary,
								 URL url, Class<?> typeParameter, List<IcaptorPameterContext> pameterContexts) {
		this.nameBot = nameBot;
		this.classLoader = classLoader;
		this.method = method;
		this.endpoint = endpoint;
		this.nameJar = nameJar;
		this.inputDictionary = inputDictionary;
		this.outputDictionary = outputDictionary;
		this.url = url;
		this.typeParameter = typeParameter;
		this.pameterContexts = pameterContexts;
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
	
	public Class<?> getTypeParameter() {
		return typeParameter;
	}

	public List<IcaptorPameterContext> getPameterContexts() {
		return pameterContexts;
	}

	public void setPameterContexts(List<IcaptorPameterContext> pameterContexts) {
		this.pameterContexts = pameterContexts;
	}
	
}