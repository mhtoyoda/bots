package com.fiveware.controller.upload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.fiveware.config.ICaptorApiProperty;
import com.fiveware.security.util.SpringSecurityUtil;

/**
 * Created by valdisnei on 29/08/2017.
 */
@RestController
@RequestMapping("/api/bot")
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

        String url = String.format("%s/api/bot/%s/upload" ,iCaptorApiProperty.getServer().getHost(),nameBot) ;

        DeferredResult<ResponseEntity<String>> resultado = new DeferredResult<>();

        Thread[] thread = new Thread[file.length];

        for (int i = 0; i < file.length; i++) {
            thread[i] = new Thread(getTarget(file[i], details, url, resultado));
        }
        for (int i = 0; i < file.length; i++) {
            thread[i].start();
        }
        return resultado;
    }

    private Runnable getTarget(@RequestParam("file") MultipartFile file,
                               @RequestHeader("Authorization") String details,
                               String url, DeferredResult<ResponseEntity<String>> resultado) {
        return new Runnable() {
            @Override
            public void run() {
                String tempFileName = null;
                    LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
                    map.add("file", new FileSystemResource(file.getOriginalFilename()));
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                    headers.add("user", SpringSecurityUtil.decodeAuthorizationKey(details));

                    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
                    ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            }
        };
    }

}
