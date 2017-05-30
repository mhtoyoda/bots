package com.fiveware.model;

import java.io.Serializable;

public class BotClassLoaderContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5344664787240666880L;
	
	private final String classLoader;
	private final String method;
	private final String endpoint;
	private final String nameJar;

	public BotClassLoaderContext(String classLoader, String method, String endpoint, String nameJar) {
		super();
		this.classLoader = classLoader;
		this.method = method;
		this.endpoint = endpoint;
		this.nameJar = nameJar;
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
}