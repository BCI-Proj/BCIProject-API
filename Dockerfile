# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y maven

WORKDIR /app
COPY pom.xml . 
COPY src /app/src

RUN mvn clean install -DskipTests 
EXPOSE 8080

CMD ["java", "-jar", "target/bci-api-0.0.1-SNAPSHOT.jar"]
