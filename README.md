# icaptor-automation

Install
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

start monitoring (icaptor-monitoring)
- ```cd icaptor-monitoring```
- ```java -jar -Dspring.profiles.active=dev target/icaptor-monitoring-0.0.1-SNAPSHOT.jar```

Para executar o agent necessario mudar o ip que é informado na linha de commando:  
java -Dspring.profiles.active=dev  -Dicaptor.elasticsearch=http://54.232.96.63:9200 -Dicaptor.redis=http://54.232.96.63:8086  -Dicaptor.server.host=54.232.96.63:8082 -Dicaptor.data-source.host=http://54.232.96.63:8085 -Dicaptor.broker.host=54.232.96.63 -Dicaptor.broker.port=15672 -Djava.awt.headless=false -jar target/icaptor-agent-0.0.1-SNAPSHOT.jar