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


    @Value("${worker.in.file}")
    private String workerInFile;


    @Autowired
    private MessageSource messageSource;


    @Autowired
	private RunningWatchServiceRecursive runningWatcher;

	@PostConstruct
	public void initialize() {
		if (!new File(workerInFile).exists()) {
			if (!new File(workerInFile).mkdirs())
				throw new RuntimeException(messageSource.getMessage("create.workerDir", new Object[]{workerInFile}, null));
		}

//		if (!new File(workDirFileOut).exists()) {
//			if (!new File(workDirFileOut).mkdirs())
//				throw new RuntimeException(
//						messageSource.getMessage("create.workerDirFileOut", new Object[]{workDirFileOut}, null));
//		}

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
                runningWatcher.run(workerInFile);
			}
		}, "READ INPUT FILE: "+ workerInFile);

		thread.start();
	}


}