package com.fiveware.controller.upload;

import com.fiveware.config.ICaptorApiProperty;
import com.fiveware.security.util.SpringSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
                                                         @RequestHeader("Authorization") String details) throws Exception {

        String url = String.format("%s/api/bot/%s/upload", iCaptorApiProperty.getServer().getHost(), nameBot);

        DeferredResult<ResponseEntity<String>> resultado = new DeferredResult<>();

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        for (int i = 0; i < file.length; i++) {

            if (file[i].getBytes().length == 0)
                throw new Exception("Arquivo nao encontrado!");

            final MultipartFile multipartFile = file[i];

            try {
                ByteArrayResource bytes = new ByteArrayResource(multipartFile.getBytes()) {
                    @Override
                    public String getFilename() {
                        return multipartFile.getOriginalFilename();
                    }
                };
                map.add("file", bytes);
            } catch (IOException e) {
                logger.error("{}", e);
                throw new Exception("Arquivo nao encontrado!");
            }

//            thread[i] = new Thread(getTarget(map, details, url, resultado));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("user", SpringSecurityUtil.decodeAuthorizationKey(details));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        try {
           restTemplate.exchange(url, HttpMethod.POST, (HttpEntity<?>) requestEntity, String.class);
           resultado.setResult(ResponseEntity.ok().body("OK"));

        } catch (HttpClientErrorException e) {
            resultado.setResult(ResponseEntity.badRequest().body(e.getResponseBodyAsString()));
        } catch (Exception e) {
            resultado.setResult(ResponseEntity.badRequest().body(e.getMessage()));
        }
        return resultado;
    }

    private Runnable getTarget(LinkedMultiValueMap<String, Object> map,
                               String details,
                               String url, DeferredResult<ResponseEntity<String>> resultado)  {
        return new Runnable() {
            @Override
            public void run() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                headers.add("user", SpringSecurityUtil.decodeAuthorizationKey(details));

                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);

                restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            }
        };
    }

}
