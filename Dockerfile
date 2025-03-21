# Use the official OpenJDK 17 image
FROM openjdk:17

# Set working directory inside the container
WORKDIR /app

# Copy the compiled Java application JAR file into the container
COPY ./target/ecomm_java_backend.jar /app

# Expose the port the Spring Boot application runs on
EXPOSE 5454

# Command to run the application
CMD ["java", "-jar", "/app/ecomm_java_backend.jar"]
