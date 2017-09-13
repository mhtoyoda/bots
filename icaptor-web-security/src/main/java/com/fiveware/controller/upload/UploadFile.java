package com.fiveware.controller.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.fiveware.config.ICaptorApiProperty;
import com.fiveware.security.SpringSecurityUtil;

/**
 * Created by valdisnei on 29/08/2017.
 */
@RestController
@RequestMapping("/bot")
public class UploadFile {


    @Autowired
    private ICaptorApiProperty iCaptorApiProperty;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/{nameBot}/upload")
    @PreAuthorize("hasAuthority('ROLE_AGENT_LIST') and #oauth2.hasScope('write')")
    public DeferredResult<ResponseEntity<String>> upload(@PathVariable String nameBot,
                                                         @RequestParam("file") MultipartFile[] file,
                                                         @RequestHeader("Authorization") String details) {

        String url = String.format("%s/%s" ,iCaptorApiProperty.getDataSource().getHost(),nameBot) ;

        DeferredResult<ResponseEntity<String>> resultado = new DeferredResult<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
                map.add("file", new FileSystemResource(file[0].getOriginalFilename()));
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                headers.add("user", SpringSecurityUtil.decodeAuthorizationKey(details));

                HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
                ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

                resultado.setResult(ResponseEntity.ok().body("OK"));

            }
        });
        thread.start();

        return resultado;
    }

}
