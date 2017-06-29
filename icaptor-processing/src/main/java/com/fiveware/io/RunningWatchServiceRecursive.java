package com.fiveware.io;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fiveware.exception.AttributeLoadException;

/**
 * Created by valdisnei on 05/06/17.
 */
@Service
public abstract class RunningWatchServiceRecursive {

    private static Logger log = LoggerFactory.getLogger(RunningWatchServiceRecursive.class);

    private static final Map<WatchKey, Path> keyPathMap = new HashMap<>();

    protected abstract void includeAction(Path path) throws IOException, AttributeLoadException;
    protected abstract void excludeAction(Path path) throws IOException, AttributeLoadException;
    protected abstract boolean isValidTypeFile(Path file);
    

    public void run(String directory) {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            registerDir(Paths.get(directory), watchService);
            startListening(watchService);

        } catch (IOException e) {
            log.error("problema Up WatchServe: ", e);
        } catch (Exception e) {
            log.error("problema Up WatchServe: ", e);
        }
    }

    private void registerDir(Path path, WatchService watchService) throws IOException, AttributeLoadException {
        if (!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)){
            includeAction(path);
            return;
        }

        log.info("registering: {}", path);

        WatchKey key = path.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);

        keyPathMap.put(key, path);

        for (File f : path.toFile().listFiles())
                registerDir(f.toPath(), watchService);

    }

    private void startListening(WatchService watchService) throws Exception {
        while (true) {
            WatchKey queuedKey = watchService.take();
            for (WatchEvent<?> watchEvent : queuedKey.pollEvents()) {

                log.debug("Event ({}), context ({}) ", watchEvent.kind(), watchEvent.context());

                if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    // this is not a complete path
                    Path path = (Path) watchEvent.context();
                    // need to get parent path
                    Path parentPath = keyPathMap.get(queuedKey);
                    // get complete path
                    path = parentPath.resolve(path);

                    if (isValidTypeFile(path)) {
                        registerDir(path, watchService);
                    }
                }

                if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                    Path path = (Path) watchEvent.context();
                    // need to get parent path
                    excludeAction(path);
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

}
