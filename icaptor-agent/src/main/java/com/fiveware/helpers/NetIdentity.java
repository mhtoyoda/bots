package com.fiveware.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

public class NetIdentity {

    final static String SPEC = "https://api.ipify.org";

    static Logger logger = LoggerFactory.getLogger(NetIdentity.class);

    public static String ipAddress() {
        String ipAddress = null;
        try (java.util.Scanner s = new java.util.Scanner(new java.net.URL(SPEC).openStream(), "UTF-8").useDelimiter("\\A")) {
            ipAddress = s.next();
        } catch (java.io.IOException e) {
            logger.error("{}", e);
        }
        return ipAddress;
    }

}
