package com.fiveware.directories;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fiveware.io.InitializeWatchService;

/**
 * Created by valdisnei on 30/05/17.
 */
@Component
public class StartReadFiles extends InitializeWatchService {


    @Value("${worker.in.file}")
    private String workerInFile;

	@Autowired
	private MessageSource messageSource;

	@Override
	public String getWorkerDir() {
		return this.workerInFile;
	}

	@Override
	public void readFiles() {
		if (!new File(workerInFile).exists()) {
			if (!new File(workerInFile).mkdirs())
				throw new RuntimeException(messageSource.getMessage("create.workerDir", new Object[]{workerInFile}, null));
		}
	}
}