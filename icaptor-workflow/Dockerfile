FROM anapsix/alpine-java
VOLUME target
ADD target/icaptor-workflow-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
#ENTRYPOINT ["java","-Dspring.profiles.active=dev","-Dicaptor.server.host=192.168.0.27:8082","-Dicaptor.data-source.host=http://192.168.0.27:8085","-Dicaptor.broker.host=192.168.0.27","-Djava.security.egd=file:/dev/./urandom","-jar","-Duser.timezone=America/Sao_Paulo","/app.jar"]
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000","-Djava.security.egd=file:/dev/./urandom","-jar","-Duser.timezone=America/Sao_Paulo","/app.jar"]