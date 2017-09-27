package com.fiveware.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("icaptor")
public class ICaptorApiProperty {

    private final DataSource dataSource = new DataSource();
    private final Seguranca seguranca = new Seguranca();
    private Server server = new Server();
    private String originPermitida;
    private Broker broker = new Broker();

    private String elasticSearch;


    public DataSource getDataSource() {return dataSource;}
    public Seguranca getSeguranca() {
        return seguranca;
    }
    public String getOriginPermitida() {
        return originPermitida;
    }
    public void setOriginPermitida(String originPermitida) {
        this.originPermitida = originPermitida;
    }
    public Server getServer() {return server;}
    public Broker getBroker() {return broker;}

    public String getElasticSearch() {
        return elasticSearch;
    }

    public void setElasticSearch(String elasticSearch) {
        this.elasticSearch = elasticSearch;
    }

    public static class Seguranca {

        private boolean enableHttps;

        public boolean isEnableHttps() {
            return enableHttps;
        }

        public void setEnableHttps(boolean enableHttps) {
            this.enableHttps = enableHttps;
        }

    }

    public static class DataSource {
        private String host;
        private String port;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }
    }

    public static class Server {
        private String host;
        private String name;
        private String ip;

        public String getHost() {return host;}
        public void setHost(String host) {this.host = host;}

        public String getName() {return name;}
        public void setName(String name) {this.name = name;}

        public String getIp() {return ip;}
        public void setIp(String ip) {this.ip = ip;}
    }

    public static class Broker {
        private String user;
        private String pass;
        private String host;
        private Integer port;
        private String queueSendSchedularTime;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getQueueSendSchedularTime() {
            return queueSendSchedularTime;
        }

        public void setQueueSendSchedularTime(String queueSendSchedularTime) {
            this.queueSendSchedularTime = queueSendSchedularTime;
        }
    }

}