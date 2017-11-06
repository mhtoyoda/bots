FROM frolvlad/alpine-oraclejdk8:slim
VOLUME target
ADD target/icaptor-redis-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8009","-Djava.security.egd=file:/dev/./urandom","-jar","-Duser.timezone=America/Sao_Paulo","/app.jar"]