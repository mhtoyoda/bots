package com.fiveware.controller.upload;

import com.fiveware.config.ICaptorApiProperty;
import com.fiveware.security.util.SpringSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by valdisnei on 29/08/2017.
 */
@RestController
@RequestMapping("/bot")
public class UploadFile {

    static Logger logger = LoggerFactory.getLogger(UploadFile.class);

    @Autowired
    private ICaptorApiProperty iCaptorApiProperty;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/{nameBot}/upload")
    @PreAuthorize("hasAuthority('ROLE_AGENT_LIST') and #oauth2.hasScope('write')")
    public DeferredResult<ResponseEntity<String>> upload(@PathVariable String nameBot,
                                                         @RequestParam("file") MultipartFile[] file,
                                                         @RequestHeader("Authorization") String details) {

        String url = String.format("%s/api/upload/%s" ,iCaptorApiProperty.getServer().getHost(),nameBot) ;

        DeferredResult<ResponseEntity<String>> resultado = new DeferredResult<>();

        Thread[] thread = new Thread[file.length];

        for (int i = 0; i < file.length; i++) {
            thread[i] = new Thread(getTarget(file, details, url, resultado));
        }
        for (int i = 0; i < file.length; i++) {
            thread[i].start();
        }
        return resultado;
    }

    private Runnable getTarget(@RequestParam("file") MultipartFile[] file,
                               @RequestHeader("Authorization") String details,
                               String url, DeferredResult<ResponseEntity<String>> resultado) {
        return new Runnable() {
            @Override
            public void run() {
                String tempFileName = null;
                try {
                    tempFileName = "/tmp/" + file[0].getOriginalFilename() +"_"+Thread.currentThread().getName();
                    FileOutputStream fo = new FileOutputStream(tempFileName);
                    fo.write(file[0].getBytes());
                    fo.close();

                    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
                    map.add("file", new FileSystemResource(tempFileName));
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                    headers.add("user", SpringSecurityUtil.decodeAuthorizationKey(details));

                    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
                    ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                        File f = new File(tempFileName);
                        logger.debug("deleting temp file {}",tempFileName);
                        f.delete();
                    resultado.setResult(ResponseEntity.ok().body("OK"));
                }
            }
        };
    }

}
