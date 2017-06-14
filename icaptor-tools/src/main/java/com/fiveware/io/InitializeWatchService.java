package com.fiveware.io;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

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
		}, "READ INPUT FILE: "+ getWorkerDir());

		thread.start();
	}

	public abstract String getWorkerDir();

	public abstract void readFiles();




}