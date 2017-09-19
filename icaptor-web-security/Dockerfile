FROM anapsix/alpine-java
VOLUME target
ADD target/icaptor-web-security-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Dicaptor.server.host=192.168.0.27:8082","-Dicaptor.data-source.host=http://192.168.0.27:8085","-Djava.security.egd=file:/dev/./urandom","-jar","-Duser.timezone=America/Sao_Paulo","/app.jar"]