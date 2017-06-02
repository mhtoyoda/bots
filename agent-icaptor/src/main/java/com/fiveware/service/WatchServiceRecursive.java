package com.fiveware.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.loader.JarConfiguration;
import com.fiveware.loader.JarMethod;

/**
 * Created by valdisnei on 30/05/17.
 */
@Component
public class WatchServiceRecursive {
    
	private Logger log = LoggerFactory.getLogger(WatchServiceRecursive.class);
	
	private static final Map<WatchKey, Path> keyPathMap = new HashMap<>();

    @Autowired
    private LoadBot loadJar;

    @Value("${worker.dir}")
    private String workerDir;

    @Value("${bot.extension.file}")
    private String extensionFile;
    
    @Autowired
    private JarConfiguration jarConfiguration;
    
    @Autowired
    private JarMethod jarMethod;
    
    @PostConstruct
    public void initialize () {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                upWathservice();
            }
        });

        thread.start();
    }

    private void upWathservice() {
        try (
            WatchService watchService = FileSystems.getDefault().newWatchService()) {
            registerDir(Paths.get(workerDir), watchService);
            startListening(watchService);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerDir (Path path, WatchService watchService) throws IOException, AttributeLoadException {
        if (!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
        	if (isValidFileType(path)) {
                loadJar.load(path.toFile());  
                jarConfiguration.saveConfigurations(path.toFile().getAbsoluteFile().getPath());
                jarMethod.readConfigurations(path.toFile().getAbsoluteFile().getPath());
            }
        	return;
        }

        log.info("registering: {}", path);

        WatchKey key = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);
        keyPathMap.put(key, path);

        for (File f : path.toFile().listFiles()) {
            registerDir(f.toPath(), watchService);
        }
    }

    private void startListening (WatchService watchService) throws Exception {
        while (true) {
            WatchKey queuedKey = watchService.take();
            for (WatchEvent<?> watchEvent : queuedKey.pollEvents()) {
                System.out.printf("Event... kind=%s, count=%d, context=%s Context type=%s%n",
                        watchEvent.kind(),
                        watchEvent.count(), watchEvent.context(), ((Path) watchEvent
                                .context()).getClass());

                if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    //this is not a complete path
                    Path path = (Path) watchEvent.context();
                    //need to get parent path
                    Path parentPath = keyPathMap.get(queuedKey);
                    //get complete path
                    path = parentPath.resolve(path);

                    if (isValidFileType(path)) {
                        loadJar.load(path.toFile());
                        registerDir(path, watchService);
                    }
                }
                if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                    Path path = (Path) watchEvent.context();                    
                    if (isValidFileType(path)) {
                       jarConfiguration.removeBot(path.toString());
                    }
                }
            }
            if (!queuedKey.reset()) {
                keyPathMap.remove(queuedKey);
            }
            if (keyPathMap.isEmpty()) {
                break;
            }
        }
    }

    private boolean isValidFileType(Path file) {
        return (file.toString().endsWith(extensionFile));
    }
}