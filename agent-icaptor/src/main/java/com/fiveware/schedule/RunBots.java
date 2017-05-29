package com.fiveware.schedule;

import com.fiveware.converter.ConverterRecordLine;
import com.fiveware.util.PropertiesCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by valdisnei on 26/05/17.
 */

@Component
public class RunBots {


    static Logger logger = LoggerFactory.getLogger(RunBots.class);

    @Value("${loader.path}")
    private String directory;

    @Scheduled(fixedRate = 5000)
    public void run() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        File file = new File(directory);
        JarFile jarFile = new JarFile(file);

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
        Object obj= execute.invoke(o, "07077170");

        System.out.println(obj);

    }

    public void wacthDirectory() throws IOException {
        final Path path = FileSystems.getDefault().getPath(System.getProperty(directory));
        System.out.println(path);
        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            final WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                final WatchKey wk = watchService.take();
                for (WatchEvent<?> event : wk.pollEvents()) {
                    //we only register "ENTRY_MODIFY" so the context is always a Path.
                    final Path changed = (Path) event.context();
                    System.out.println(changed);
                    if (changed.endsWith("myFile.txt")) {
                        System.out.println("My file has changed");
                    }
                }
                // reset the key
                boolean valid = wk.reset();
                if (!valid) {
                    System.out.println("Key has been unregisterede");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
