package com.fiveware.directories;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Created by valdisnei on 30/05/17.
 */
@Component
public class InitializeWatchService {


    @Value("${worker.dir}")
    private String workerDir;

    @Value("${worker.file}")
    private String workDirFileOut;


    @Autowired
    private MessageSource messageSource;


    @Autowired
	private RunningWatchServiceRecursive runningWatcher;

	@PostConstruct
	public void initialize() {
		if (!new File(workerDir).exists()) {
			if (!new File(workerDir).mkdirs())
				throw new RuntimeException(messageSource.getMessage("create.workerDir", new Object[]{workerDir}, null));
		}

		if (!new File(workDirFileOut).exists()) {
			if (!new File(workDirFileOut).mkdirs())
				throw new RuntimeException(
						messageSource.getMessage("create.workerDirFileOut", new Object[]{workDirFileOut}, null));
		}

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
                runningWatcher.run(workerDir);
			}
		}, "Watcher : "+ workerDir );

		thread.start();
	}


}