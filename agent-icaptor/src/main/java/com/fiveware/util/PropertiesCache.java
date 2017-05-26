package com.fiveware.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Created by valdisnei on 20/01/17.
 */
@Component
public class PropertiesCache {

    Logger logger = LoggerFactory.getLogger(PropertiesCache.class);


    private final Properties configProp = new Properties();


    public void load(ClassLoader classLoader, String path)
    {
        try {
            InputStream in = classLoader.getResourceAsStream(path);
            configProp.load(in);
        } catch (IOException e) {
            logger.error("erro ao abrir arquivo properties",e);
        }
    }


    public String getProperty(String key){
        return configProp.getProperty(key);
    }

    public Set<String> getAllPropertyNames(){
        return configProp.stringPropertyNames();
    }

    public boolean containsKey(String key){
        return configProp.containsKey(key);
    }
}
