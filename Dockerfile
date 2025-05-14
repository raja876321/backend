# Use a base image with JDK 17
FROM openjdk:17-jdk-slim


# Expose the application port (change if needed)
EXPOSE 8181

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "Mobistock-0.0.1-SNAPSHOT.jar"]