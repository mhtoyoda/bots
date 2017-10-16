package com.fiveware.service;

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ServiceElasticSearch {
    private static Logger logger = LoggerFactory.getLogger(ServiceElasticSearch.class);

    @Autowired
    private ApiUrlPersistence apiUrlPersistence;

    @Autowired
    private RestTemplate restTemplate;

    public ServiceElasticSearch() {
    }

    public ServiceElasticSearch(ApiUrlPersistence apiUrlPersistence, RestTemplate restTemplate) {
        this.apiUrlPersistence = apiUrlPersistence;
        this.restTemplate = restTemplate;
    }

    public String log(Log task) {
        String url = apiUrlPersistence.endPointElasticSearch("icaptor-automation", task.getClass().getSimpleName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<Object>(task, headers);
        try {
            ResponseEntity<Map<String, Object>> exchange = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {
            });
            return exchange.getBody().get("_id").toString();
        }catch (Exception e){
            error(e);
        }
        return null;
    }


    public void error(Exception e) {
        Error message = new Error(e.getStackTrace(), e.getCause(), e.getMessage());
        String url = "http://54.232.96.63:9200/icaptor-automation/error";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<Object>(message, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(url, entity, Object.class);
    }

    public int delete(Log task, String id) {
        String url = apiUrlPersistence.endPointElasticSearch("icaptor-automation",
                task.getClass().getSimpleName().toString()
                        .concat("/")
                        .concat(id));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<Object>(task, headers);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Object.class);

        return 1;
    }



    static class Error {

        private final StackTraceElement[] stackTraces;
        private String mensagem;
        private Throwable cause;

        public Error(StackTraceElement[] stackTrace, Throwable cause, String message) {
            this.stackTraces = stackTrace;
            this.cause = cause;
            this.mensagem = message;
        }


        public StackTraceElement[] getStackTraces() {
            return stackTraces;
        }

        public void setCause(Throwable cause) {
            this.cause = cause;
        }

        public Throwable getCause() {
            return cause;
        }

        public void setException(java.lang.Error exception) {
            this.cause = exception;
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }
    }
}