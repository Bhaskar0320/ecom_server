# Use the official OpenJDK 17 image from Docker Hub
FROM openjdk:17

# Set working directory inside the container
WORKDIR /app

# Copy the compiled Java application JAR file into the container
COPY ./target/ecomm_java_backend.jar /app/app.jar 

# Expose the correct port (Verify this in application.properties)
EXPOSE 5454

# Command to run the application
CMD [ "java", "-jar", "/app/app.jar" ]
