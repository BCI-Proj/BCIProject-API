# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled .jar file into the container
COPY target/bci-api-0.0.1-SNAPSHOT.jar /app/bci-api-0.0.1-SNAPSHOT.jar

# Expose the port that your application will run on (default Spring Boot port is 8080)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/bci-api-0.0.1-SNAPSHOT.jar"]
