package com.fiveware.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by valdisnei on 30/05/17.
 */
@Component
public abstract class InitializeWatchService {

    @Autowired
    private MessageSource messageSource;

	@Autowired
	private RunningWatchServiceRecursive runningWatcher;

	@PostConstruct
	public void initialize() {
		readFiles();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				runningWatcher.run(getWorkerDir());
			}
		}, "FILES WATCHER: "+ getWorkerDir().toUpperCase());

		thread.start();
	}

	protected abstract String getWorkerDir();

	protected abstract void readFiles();
}