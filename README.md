# icaptor-automation

Compile
-  ``` mvn clean package```


Start Server (cloud)
-  ```java -jar target/icaptor-cloud-0.0.1-SNAPSHOT.jar``` 

start client (agent)
- ```java -Dspring.config.location=file:$HOME/.icaptor-out-file/icaptor-platform.properties -jar target/icaptor-agent-0.0.1-SNAPSHOT.jar```


