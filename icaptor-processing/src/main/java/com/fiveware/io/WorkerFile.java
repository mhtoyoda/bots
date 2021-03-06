package com.fiveware.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class WorkerFile implements Runnable {

    static Logger logger = LoggerFactory.getLogger(WorkerFile.class);

    private MultipartFile file;
    private DeferredResult<ResponseEntity<String>> resultado;

    private ReadInputFile readInputFile;

    private String nameBot;

	private Long userId;

    public WorkerFile(Long userId, String nameBot, MultipartFile file, ReadInputFile readInputFile,
                      DeferredResult<ResponseEntity<String>> resultado) {
        this.userId = userId;
    	this.file = file;
        this.resultado = resultado;
        this.readInputFile = readInputFile;
        this.nameBot = nameBot;
    }

    @Override
    public void run() {
        try {
            readInputFile.readFile(userId, nameBot, file, resultado);
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }
}