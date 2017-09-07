package com.fiveware.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class WorkerFile implements Runnable {

    static Logger logger = LoggerFactory.getLogger(WorkerFile.class);

    private MultipartFile[] files;
    private DeferredResult<ResponseEntity<String>> resultado;

    private ReadInputFile readInputFile;

    private String nameBot;

    public WorkerFile(String nameBot, MultipartFile[] files, ReadInputFile readInputFile,
                         DeferredResult<ResponseEntity<String>> resultado) {
        this.files = files;
        this.resultado = resultado;
        this.readInputFile=readInputFile;
        this.nameBot=nameBot;
    }

    @Override
    public void run() {
        try {
            readInputFile.readFile(nameBot,files[0].getOriginalFilename(),
                    files[0].getInputStream(),resultado);
        } catch (IOException e) {
            logger.error("{}",e);
            resultado.setResult(ResponseEntity.badRequest().body("ERROR"));
        }

    }
}