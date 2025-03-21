# Use a lightweight JDK image (Alpine-based for reduced size)
FROM eclipse-temurin:17-jdk-alpine

# Install curl (optional, useful for debugging or health checks)
RUN apk add --no-cache curl

# Set working directory inside the container
#WORKDIR /app

# Copy the compiled Java application JAR file into the container
COPY target/ecomm_java_backend.jar /ecomm_java_backend.jar

# Expose the application's port
EXPOSE 5454

# Command to run the application
CMD ["java", "-jar", "/ecomm_java_backend.jar"]
