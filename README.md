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

# Start Resources
- docker-compose up -d icaptor-mysql icaptor-alpine-redis icaptor-broker

# Start ICaptor-ELK (opcional)
- Para inicializar o [ICaptor-ELK](https://github.com/fiveware-solutions/icaptor-automation/tree/master/icaptor-elk)  
  **Obs.:** 
  Estamos usando por default o ELK instalado no servidor de Testes (QA).  
  *Motivo:* Não sobrecarregar o ambiente de desenvolvimento.
     

## Para compilar os micros serviços
-  ``` mvn clean install```

### Start Data (icaptor-data)
-  ```cd icaptor-persistence``` 
-  ```java -jar target/icaptor-persistence-0.0.1-SNAPSHOT.jar``` 

### Start Server (icaptor-cloud)
-  ```cd icaptor-cloud``` 
-  ```java -jar -Dspring.profiles.active=dev target/icaptor-cloud-0.0.1-SNAPSHOT.jar``` 

### Start Redis (icaptor-redis)
- ```cd icaptor-redis```
- ```java -jar -Dspring.profiles.active=dev target/icaptor-redis-0.0.1-SNAPSHOT.jar```

###  Start Monitoring (icaptor-monitoring)
- ```cd icaptor-monitoring```
- ```java -jar -Dspring.profiles.active=dev target/icaptor-monitoring-0.0.1-SNAPSHOT.jar```

###  Start Api (icaptor-web-security)
- ```cd icaptor-web-security```
- ```java -jar -Dspring.profiles.active=dev target/icaptor-web-security-0.0.1-SNAPSHOT.jar```

### Start Client (icaptor-agent)
- ```cd icaptor-agent```
- ```java -jar -Dspring.profiles.active=dev target/icaptor-agent-0.0.1-SNAPSHOT.jar```

## ICaptor Developers



Lista de  [developers](https://github.com/orgs/fiveware-solutions/teams/icaptor-developers/members) quem participa do projeto.
