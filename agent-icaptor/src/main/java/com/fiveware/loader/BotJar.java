package com.fiveware.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by valdisnei on 30/05/17.
 */
@Component
public class BotJar {

    public static final String APPLICATION_PROPERTIES = "application.properties";

    private File file;
    private InputStream input;
    private Properties configProp;

    @Value("${loader.path}")
    private String directory;

    @PostConstruct
    public void initicialize() throws IOException {
    	System.out.println("POST");
        this.file = new File(directory);
        JarFile jarFile = new JarFile(file);
        JarEntry jarEntry = jarFile.getJarEntry(APPLICATION_PROPERTIES);
        JarEntry fileEntry = jarFile.getJarEntry(jarEntry.getName());
        this.input = jarFile.getInputStream(fileEntry);
        this.configProp = new Properties();
        this.configProp.load(this.input);
    }

    public File getFile() {
        return file;
    }

    public InputStream getInput() {
        return input;
    }

    public String getConfigProp(String key) {
        return configProp.getProperty(key);
    }
}
