package com.fiveware.dsl;

import com.fiveware.UtilsPages;
import com.fiveware.core.PageIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by valdisnei on 27/06/17.
 */
class ConvertHtmlToPdf {

    static Logger logger = LoggerFactory.getLogger(ConvertHtmlToPdf.class);
    private String url;
    private String outPut;



    protected ConvertHtmlToPdf(String url) {
        this.url = url;
    }

    protected void buildToFile() {
        ResponseEntity<byte[]> response = getResponseEntity();

        if (response.getStatusCode() == HttpStatus.OK) {
            writerFile(response);
        }
    }
    protected PageIterator buildToPages() {
        ResponseEntity<byte[]> response = getResponseEntity();

        if (response.getStatusCode() == HttpStatus.OK) {
            return writerBytes(response);
        }

        return null;
    }

    private ResponseEntity<byte[]> getResponseEntity() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(
                new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<String>(headers);


        final String downloadUrl="http://localhost:8080/convert?auth=arachnys-weaver&url="+url;


        return restTemplate.exchange(downloadUrl, HttpMethod.GET, entity,
                                                                    byte[].class, "0");
    }

    private void writerFile(ResponseEntity<byte[]> response) {
        try {
            Files.write(Paths.get(this.outPut), response.getBody());
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    private PageIterator writerBytes(ResponseEntity<byte[]> response) {
        try {
            Files.write(Paths.get(this.outPut), response.getBody());
            return UtilsPages.pages(this.outPut);

        } catch (IOException e) {
            logger.error("{}",e);
        }

        return null;

    }


    public void setOutPut(String outPut) {
        this.outPut = outPut;
    }
}
