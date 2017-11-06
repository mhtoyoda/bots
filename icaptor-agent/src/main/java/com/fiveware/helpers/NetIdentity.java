package com.fiveware.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

public class NetIdentity {

    final static String SPEC = "http://bot.whatismyipaddress.com";

    static Logger logger = LoggerFactory.getLogger(NetIdentity.class);

    public static String ipAddress() {
        URL url = null;
        BufferedReader in = null;
        String ipAddress = "";
        try {
            url = new URL(SPEC);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            ipAddress = in.readLine().trim();
            if (!(ipAddress.length() > 0))
                ipAddress= getIp();
        } catch (Exception ex) {
            // This try will give the Private IP of the Host.
            ipAddress= getIp();
            //ex.printStackTrace();
        }
        System.out.println("IP Address: " + ipAddress);
        return ipAddress;
    }

    private static String getIp() {
        String ipAddress;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println((ip.getHostAddress()).trim());
            ipAddress = (ip.getHostAddress()).trim();
        } catch (Exception exp) {
            ipAddress = "ERROR";
        }
        return ipAddress;
    }

    public static void main(String[] args) {
        System.out.println("ipAddress() = " + ipAddress());
    }
}
