package com.fiveware.security;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;

public final class SpringSecurityUtil {

    static  Logger logger = LoggerFactory.getLogger(SpringSecurityUtil.class);

    public static String decodeAuthorizationKey(final String basicAuthValue) {
        ObjectMapper objectMapper = new ObjectMapper();

        if (basicAuthValue == null) {
            return null;
        }


        String[] split = basicAuthValue.split("\\.");
        Object payload = null;

        final byte[] decodeBytes = Base64.decodeBase64(split[1].substring(split[1].indexOf(' ') + 1));
        String decoded = null;
        try {
            decoded = new String(decodeBytes, "UTF-8");
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

}
