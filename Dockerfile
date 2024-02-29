FROM openjdk:17-jdk-alpine
COPY target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar
ADD target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar
EXPOSE 8089
ENTRYPOINT ["java", "-Dspring.profiles.active=local-docker","-jar","/demo-0.0.1-SNAPSHOT.jar"]
