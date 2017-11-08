package com.fiveware.security.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class SpringSecurityUtil {

    public static final int SPLIT_PAYLOAD = 1;
    static  Logger logger = LoggerFactory.getLogger(SpringSecurityUtil.class);

    public static String decodeAuthorizationKey(final String basicAuthValue) {
        ObjectMapper objectMapper = new ObjectMapper();

        if (basicAuthValue == null) {
            return null;
        }


        Object payload = null;

        String[] split = basicAuthValue.split("\\.");
        final byte[] decodeBytes = Base64.decodeBase64(split[SPLIT_PAYLOAD].substring(split[SPLIT_PAYLOAD].indexOf(' ') + 1));
        try {
            String decoded = new String(decodeBytes, "UTF-8");
            payload = objectMapper.readValue(decoded, new TypeReference<LinkedHashMap<String, Object>>() {});


        } catch (final UnsupportedEncodingException e) {
            return null;
        } catch (JsonParseException e) {
            logger.error("{}",e);
        } catch (JsonMappingException e) {
            logger.error("{}",e);
        } catch (IOException e) {
            logger.error("{}",e);
        }
        return String.valueOf(payload) ;
    }

    public static Object decodeAuthorizationKey(final String basicAuthValue,String key) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        if (basicAuthValue == null) {
            return null;
        }


        String[] split = basicAuthValue.split("\\.");
        Map<String,Object> payload = null;

        final byte[] decodeBytes = Base64.decodeBase64(split[1].substring(split[1].indexOf(' ') + 1));
        String decoded = null;
        try {
            decoded = new String(decodeBytes, "UTF-8");
            payload = objectMapper.readValue(decoded, Map.class);


        } catch (final UnsupportedEncodingException e) {
            return null;
        } catch (JsonParseException e) {
            logger.error("{}",e);
        } catch (JsonMappingException e) {
            logger.error("{}",e);
        } catch (IOException e) {
            logger.error("{}",e);
        }
        return payload.get(key) ;
    }
}
