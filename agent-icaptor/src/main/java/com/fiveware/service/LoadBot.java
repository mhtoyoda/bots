package com.fiveware.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by valdisnei on 30/05/17.
 */
@Component
public class LoadBot {

	static Logger logger = LoggerFactory.getLogger(LoadBot.class);

	public static final String APPLICATION_PROPERTIES = "application.properties";

	private File file;
	private InputStream input;
	private Properties configProp;

	public void load(File directory) throws IOException {
		load(directory.getAbsoluteFile().getPath());
	}

	public void load(String directory) throws FileNotFoundException {
		this.file = new File(directory);
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(file);
			JarEntry jarEntry = jarFile.getJarEntry(APPLICATION_PROPERTIES);
			JarEntry fileEntry = jarFile.getJarEntry(jarEntry.getName());
			this.input = jarFile.getInputStream(fileEntry);
			this.configProp = new Properties();
			this.configProp.load(this.input);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error("load bot ", e);
		}
	}

	public File getFile() {
		return file;
	}

	public InputStream getInput() {
		return input;
	}

}