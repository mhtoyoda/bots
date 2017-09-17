echo on
java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Dspring.profiles.active=dev -Dicaptor.data-sources.host=192.168.0.14:8085 icaptor-agent-0.0.1-SNAPSHOT.jar