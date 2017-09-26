# icaptor-automation

Install
-  ``` mvn clean install```

Start DataBase (icaptor-persistence)
-  ```cd icaptor-persistence``` 
-  ```java -jar target/icaptor-persistence-0.0.1-SNAPSHOT.jar``` 

Start Server (icaptor-cloud)
-  ```cd icaptor-cloud``` 
-  ```java -jar -Dspring.profiles.active=dev target/icaptor-agent-0.0.1-SNAPSHOT.jar``` 

start client (icaptor-agent)
- ```cd icaptor-agent```
- ```java -jar -Dspring.profiles.active=dev target/icaptor-agent-0.0.1-SNAPSHOT.jar```

start monitoring (icaptor-monitoring)
- ```cd icaptor-monitoring```
- ```java -jar -Dspring.profiles.active=dev target/icaptor-monitoring-0.0.1-SNAPSHOT.jar```

