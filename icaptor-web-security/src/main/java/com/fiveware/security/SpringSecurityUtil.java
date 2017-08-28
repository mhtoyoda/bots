package com.fiveware.security;

import org.apache.commons.codec.binary.Base64;
import org.springframework.data.util.Pair;

import java.io.UnsupportedEncodingException;

public final class SpringSecurityUtil {

    public static Pair<String, String> decodeAuthorizationKey(final String basicAuthValue) {
        if (basicAuthValue == null) {
            return null;
        }
        final byte[] decodeBytes = Base64.decodeBase64(basicAuthValue.substring(basicAuthValue.indexOf(' ') + 1));
        String decoded = null;
        try {
            decoded = new String(decodeBytes, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            return null;
        }
        final int indexOfDelimiter = decoded.indexOf(':');
        final String username = decoded.substring(0, indexOfDelimiter);
        final String password = decoded.substring(indexOfDelimiter + 1);
        return Pair.of(username, password);
    }

}
