#base image: linux alpine os with open jdk 8
FROM maven:3.9.8-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:17-alpine
#copy jar from local into docker image
COPY --from=build /app/target/discoveryservice-0.0.1-SNAPSHOT.jar discoveryservice-0.0.1-SNAPSHOT.jar

# Expose port
EXPOSE 9999
#command line to run jar
ENTRYPOINT ["java","-jar","/discoveryservice-0.0.1-SNAPSHOT.jar"]
