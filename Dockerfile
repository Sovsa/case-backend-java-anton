#
# Build stage
#
FROM maven:3.9.7-eclipse-temurin-21 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml package
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/app/target/restservice-0.0.1-SNAPSHOT.jar"]