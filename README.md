# Icaptor Automation - Plataforma de Automação de Processos

## Prerequisites Ambiente de desenvolvimento
- JDK 1.8 or later
- Maven 3 or later
- Docker version 17.05.0-ce
- Docker-Compose version 1.17.1

Start Resources
- docker-compose up -d icaptor-mysql icaptor-alpine-redis icaptor-broker

Para compilar os micro servicos
-  ``` mvn clean install```

Start DataBase (icaptor-persistence)
-  ```cd icaptor-persistence``` 
-  ```java -jar target/icaptor-persistence-0.0.1-SNAPSHOT.jar``` 

Start Server (icaptor-cloud)
-  ```cd icaptor-cloud``` 
-  ```java -jar -Dspring.profiles.active=dev target/icaptor-cloud-0.0.1-SNAPSHOT.jar``` 

start client (icaptor-agent)
- ```cd icaptor-agent```
- ```java -jar -Dspring.profiles.active=dev target/icaptor-agent-0.0.1-SNAPSHOT.jar```
- ```java -Dspring.profiles.active=dev  -Dicaptor.elasticsearch=http://54.232.96.63:9200 -Dicaptor.redis=http://54.232.96.63:8086  -Dicaptor.server.host=54.232.96.63:8082 -Dicaptor.data-source.host=http://54.232.96.63:8085 -Dicaptor.broker.host=54.232.96.63 -Dicaptor.broker.port=15672 -Djava.awt.headless=false -jar target/icaptor-agent-0.0.1-SNAPSHOT.jar```

start monitoring (icaptor-monitoring)
- ```cd icaptor-monitoring```
- ```java -jar -Dspring.profiles.active=dev target/icaptor-monitoring-0.0.1-SNAPSHOT.jar```

start api (icaptor-web-security)
- ```cd icaptor-web-security```
- ```java -jar -Dspring.profiles.active=dev target/icaptor-web-security-0.0.1-SNAPSHOT.jar```


