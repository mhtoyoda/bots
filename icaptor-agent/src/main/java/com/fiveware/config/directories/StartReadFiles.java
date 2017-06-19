package com.fiveware.config.directories;

import com.fiveware.io.InitializeWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by valdisnei on 30/05/17.
 */
@Component
public class StartReadFiles extends InitializeWatchService {


	@Value("${io.worker.dir}")
	private String workerDir;

	@Value("${io.worker.file}")
	private String workDirFileOut;


	@Autowired
	private MessageSource messageSource;

	@Override
	public String getWorkerDir() {
		return this.workerDir;
	}

	@Override
	public void readFiles() {
		if (!new File(workerDir).exists()) {
			if (!new File(workerDir).mkdirs())
				throw new RuntimeException(
						messageSource.getMessage("create.workerDir",
								new Object[]{workerDir}, null));
		}

		if (!new File(workDirFileOut).exists()) {
			if (!new File(workDirFileOut).mkdirs())
				throw new RuntimeException(
						messageSource.getMessage("create.workerDirFileOut",
								new Object[]{workDirFileOut}, null));
		}
	}
}