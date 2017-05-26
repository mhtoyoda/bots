package com.fiveware.schedule;

import com.fiveware.util.PropertiesCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.Enumeration;
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


    @Autowired
    private PropertiesCache propertiesCache;


    @Scheduled(fixedRate = 5000)
    public void run() throws IOException {
        File file = new File(directory);

        ClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
        Enumeration<URL> en = classLoader.getResources("META-INF");

        if (en.hasMoreElements()) {
			if (en.hasMoreElements()) {
				URL url2 = en.nextElement();
				JarURLConnection urlcon = (JarURLConnection) (url2.openConnection());
				try (JarFile jar = urlcon.getJarFile();) {
					Enumeration<JarEntry> entries = jar.entries();
					while (entries.hasMoreElements()) {
						String entry = entries.nextElement().getName();
						System.out.println(entry);
                        if ("application.properties".equals(entry))
                            openProperties(classLoader, entry);
					}
				}
			}
        }
    }

    private void openProperties(ClassLoader classLoader, String entry) {
        propertiesCache.load(classLoader, entry);
        String className = propertiesCache.getProperty("icaptor.class.main");
        String method = propertiesCache.getProperty("icaptor.method.execute");

        Class cls = null;
        try {
            cls = classLoader.loadClass(className);
            Object o = cls.newInstance();
            Method execute = cls.getMethod(method, Object.class);
            execute.invoke(o, "07077170");

        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException | InstantiationException e) {
            logger.error("error {}", e);
        }

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
