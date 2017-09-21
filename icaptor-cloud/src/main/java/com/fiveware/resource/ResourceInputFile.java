package com.fiveware.resource;

import com.fiveware.io.WorkerFile;
import com.fiveware.io.ReadInputFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * Created by valdisnei on 06/06/17.
 */
@RestController
@RequestMapping("/api/bot")
public class ResourceInputFile {

    static Logger logger = LoggerFactory.getLogger(ResourceInputFile.class);

    @Autowired
    private ReadInputFile readInputFile;

    @GetMapping("/up")
    public String up(HttpServletRequest httpRequest){
        String remoteAddr = httpRequest.getRemoteAddr();

        logger.info("Agent UP: {}",remoteAddr);
        return "UP";
    }
    @GetMapping("/down")
    public String down(HttpServletRequest httpRequest){
        String remoteAddr = httpRequest.getRemoteAddr();

        logger.info("Agent DOWN: {}",remoteAddr);
        return "DOWN";
    }

    @PostMapping(value = "/{nameBot}/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DeferredResult<ResponseEntity<String>> upload(@PathVariable String nameBot,
                                                         @RequestParam("file") MultipartFile file, HttpServletRequest httpRequest){

        DeferredResult<ResponseEntity<String>> resultado = new DeferredResult<>();

        Thread thread = new Thread(new WorkerFile(nameBot, file, readInputFile, resultado));
        thread.start();

        return resultado;
    }

}
