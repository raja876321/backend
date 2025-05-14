# Use a lightweight JDK 17 base image
FROM openjdk:17-jdk-slim

# Copy the built JAR file into the container
COPY target/Mobistock-0.0.1-SNAPSHOT.jar app.jar

# Expose the Spring Boot application port
EXPOSE 8181

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
