package com.fiveware.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by valdisnei on 29/05/17.
 */
@Service
public class ServiceBot {

    static Logger logger = LoggerFactory.getLogger(ServiceBot.class);

    @Value("${loader.path}")
    private String directory;


    public Object callBot(String cep) {
        File file = new File(directory);
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);
            //        jarFile.stream().forEach( jarEntry -> System.out.println("jarEntry = " + jarEntry.getName()) );

            JarEntry jarEntry = jarFile.getJarEntry("application.properties");
            JarEntry fileEntry = jarFile.getJarEntry(jarEntry.getName());
            InputStream input = jarFile.getInputStream(fileEntry);
            Properties configProp = new Properties();
            configProp.load(input);
            String className2 = configProp.getProperty("icaptor.class.main");
            String method = configProp.getProperty("icaptor.method.execute");


            ClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
            Class cls = classLoader.loadClass(className2);
            Object o = cls.newInstance();
            Method execute = cls.getMethod(method, String.class);
            Object obj = execute.invoke(o, cep);

            return obj;

        } catch (IOException | ClassNotFoundException |
                IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            logger.error(" ServiceBot: {}  ", e);
        }

        return null;

    }

}
