# Use an OpenJDK base image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY pom.xml . 
COPY src /app/src

RUN mvn clean install -DskipTests # If you're using Maven
EXPOSE 8080

CMD ["java", "-jar", "target/bci-api-0.0.1-SNAPSHOT.jar"]
