# ![Fiveware](https://avatars1.githubusercontent.com/u/23555013?s=200&v=4) ICaptor Automation

### Pré-requisitos para o Ambiente de desenvolvimento
- JDK 1.8 or later
- Maven 3 or later
- Docker version 17.05.0-ce
- Docker-Compose version 1.17.1

# Instalação
* [Docker](https://atutoriais.com/linux/como-instalar-o-docker-no-ubuntu-16-04/) - Instalação versão 17.05.0-ce
* [Docker-compose](https://www.digitalocean.com/community/tutorials/how-to-install-docker-compose-on-ubuntu-16-04) - Instalação versão 1.17 
* [MAVEN 3](https://www.vultr.com/docs/how-to-install-apache-maven-on-ubuntu-16-04) - Instalar e copiar arquivo [settings](https://github.com/fiveware-solutions/icaptor-automation/blob/master/settings.xml) para o diretorio conf
* [JDK 1.8](https://www.vultr.com/docs/how-to-install-apache-maven-on-ubuntu-16-04) - Instalação Java 8 

 **Depois de Instalar**

 * mkdir [nome-diretorio] && cd [nome-diretorio]
 * git clone https://github.com/fiveware-solutions/icaptor-automation.git
 * cd icaptor-automation

# Servers - Mysql, Redis e RabbitMQ
- cd [nome-diretorio]/icaptor-automation
- docker-compose up -d icaptor-mysql icaptor-alpine-redis icaptor-broker

# ICaptor-ELK (opcional)
- Para inicializar o [ICaptor-ELK](https://github.com/fiveware-solutions/icaptor-automation/tree/master/icaptor-elk)
- Usar **[http://54.232.96.63:5601/](http://54.232.96.63:5601/)** (Ambiente QA)

  **Obs.:** 
  Estamos usando por default o ELK instalado no servidor de Testes (QA).  
  *Motivo:* Não sobrecarregar o ambiente de desenvolvimento.
     

## Para compilar os micros serviços
-  ``` mvn clean install```

** Para start os micros servicos abaixo segui a sequencia:
  1o - Icaptor-data
  2o - icaptor-cloud
  3o - icaptor-redis
  4o - icaptor-monitoring
  5o - icaptor-api (Opcional, necessário se for usar aplicacao Web (UX))
  6o - icaptor-agent

### Micro service (icaptor-data)
-  ```cd icaptor-persistence``` 
-  ```java -jar -Dspring.profiles.active=dev target/icaptor-persistence-0.0.1-SNAPSHOT.jar```   

* Para Executar na sua IDE basta executar a classe [IcaptorPersistenceApplication](https://github.com/fiveware-solutions/icaptor-automation/blob/master/icaptor-persistence/src/main/java/com/fiveware/IcaptorPersistenceApplication.java):


**Obs.:** Configurar o parametro  -Dspring.profiles.active=dev na sua IDE.  
  
### Micro service (icaptor-cloud)
-  ```cd icaptor-cloud``` 
-  ```java -jar -Dspring.profiles.active=dev target/icaptor-cloud-0.0.1-SNAPSHOT.jar``` 
* Para Executar na sua IDE basta executar a classe [IcaptorPlatformApplication](https://github.com/fiveware-solutions/icaptor-automation/blob/master/icaptor-cloud/src/main/java/com/fiveware/IcaptorPlatformApplication.java):

**Obs.:** Configurar o parametro  -Dspring.profiles.active=dev na sua IDE.  
### Micro service (icaptor-redis)
- ```cd icaptor-redis```
- ```java -jar -Dspring.profiles.active=dev target/icaptor-redis-0.0.1-SNAPSHOT.jar```
* Para Executar na sua IDE basta executar a classe [ICaptorRedisApplication](https://github.com/fiveware-solutions/icaptor-automation/blob/master/icaptor-redis/src/main/java/com/redis/ICaptorRedisApplication.java):

**Obs.:** Configurar o parametro  -Dspring.profiles.active=dev na sua IDE.
### Micro service (icaptor-monitoring)
- ```cd icaptor-monitoring```
- ```java -jar -Dspring.profiles.active=dev target/icaptor-monitoring-0.0.1-SNAPSHOT.jar```
* Para Executar na sua IDE basta executar a classe [IcaptorMonitoringApplication](https://github.com/fiveware-solutions/icaptor-automation/blob/master/icaptor-monitoring/src/main/java/com/fiveware/IcaptorMonitoringApplication.java):

**Obs.:** Configurar o parametro  -Dspring.profiles.active=dev na sua IDE.
### Micro service (icaptor-api)
- ```cd icaptor-web-security```
- ```java -jar -Dspring.profiles.active=dev target/icaptor-web-security-0.0.1-SNAPSHOT.jar```
* Para Executar na sua IDE basta executar a classe [IcaptorWebSecurityApplication](https://github.com/fiveware-solutions/icaptor-automation/blob/master/icaptor-web-security/src/main/java/com/fiveware/IcaptorWebSecurityApplication.java):

**Obs.:** Configurar o parametro  -Dspring.profiles.active=dev na sua IDE.
### Micro service (icaptor-agent)
- ```cd icaptor-agent```
- ```java -jar -Dspring.profiles.active=dev target/icaptor-agent-0.0.1-SNAPSHOT.jar```

* Para Executar na sua IDE basta executar a classe [IcaptorAgentApplication](https://github.com/fiveware-solutions/icaptor-automation/blob/master/icaptor-agent/src/main/java/com/fiveware/IcaptorAgentApplication.java):

**Obs.:** Configurar o parametro  -Dspring.profiles.active=dev na sua IDE.

## ICaptor Developers



Lista de  [developers](https://github.com/orgs/fiveware-solutions/teams/icaptor-developers/members) quem participa do projeto.
