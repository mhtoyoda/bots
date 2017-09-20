FROM anapsix/alpine-java
VOLUME target
ADD target/icaptor-monitoring-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Duser.timezone=America/Sao_Paulo","/app.jar"]
