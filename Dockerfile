# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled .jar file into the container
COPY target/your-app.jar /app/your-app.jar

# Expose the port that your application will run on (default Spring Boot port is 8080)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/your-app.jar"]
