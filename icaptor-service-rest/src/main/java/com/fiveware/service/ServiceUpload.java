package com.fiveware.service;

import com.fiveware.config.ICaptorApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ServiceUpload {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ICaptorApiProperty iCaptorApiProperty;


    public void upload(MultipartFile file, String decodeAuthorization, String url, DeferredResult<ResponseEntity<String>> resultado){

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file",new ClassPathResource(file.getOriginalFilename()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        headers.add("user", decodeAuthorization);
//
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
        restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

    }
}
