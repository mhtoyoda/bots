package com.fiveware.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by valdisnei on 13/07/17.
 */
@Component
public class ServerInfo {

    @Value("${server.name}")
    private String name;

    @Value("${server.host}")
    private String host;

    @Value("${server.port}")
    private int port;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
