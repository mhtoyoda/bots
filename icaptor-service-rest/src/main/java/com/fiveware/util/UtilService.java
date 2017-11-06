package com.fiveware.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Objects;

public class UtilService {

    public static HttpEntity getCommonEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (Objects.isNull(body)) return new HttpEntity(headers);

        return new HttpEntity(body, headers);
    }

    public static ParameterizedTypeReference<Object> getParameterized() {
        return new ParameterizedTypeReference<Object>() {
        };
    }
}
